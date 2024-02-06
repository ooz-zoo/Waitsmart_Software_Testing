package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitchenLandingPage extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private OrderAdapter mOrderAdapter;
    private List<OrderItem> mOrderItems = new ArrayList<>();
    private FirebaseAuth mAuth;
    private Button btnLogout;
    private FirebaseFirestore db;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_landing_page);

        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.mykitchenview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrderAdapter = new OrderAdapter();
        mRecyclerView.setAdapter(mOrderAdapter);

        // Initialize the Handler and Runnable
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                loadOrders();
                handler.postDelayed(this, 600000); // 5000 milliseconds = 5 seconds
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start the refreshing process when the activity is resumed
        handler.postDelayed(refreshRunnable, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove the refresh callbacks when the activity is paused
        handler.removeCallbacks(refreshRunnable);
    }

    private void loadOrders() {
        CollectionReference usersCollection = db.collection("Users");

        usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    mOrderItems.clear();
                    for (QueryDocumentSnapshot userDoc : task.getResult()) {
                        String userId = userDoc.getId();
                        CollectionReference ordersCollection = usersCollection.document(userId).collection("orders");
                        ordersCollection.whereEqualTo("OrderPreparedStatus", false) // Only fetch orders not marked as ready
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> ordersTask) {
                                        if (ordersTask.isSuccessful()) {
                                            Map<String, OrderItem> orderMap = new HashMap<>();
                                            for (DocumentSnapshot orderDoc : ordersTask.getResult()) {
                                                String transactionId = orderDoc.getString("TransactionID");
                                                String itemName = orderDoc.getString("ItemName");
                                                String itemQuantity = orderDoc.getString("ItemQuantity");
                                                boolean orderReady = orderDoc.getBoolean("OrderPreparedStatus");

                                                OrderItem orderItem = orderMap.get(transactionId);
                                                if (orderItem == null) {
                                                    orderItem = new OrderItem(transactionId, userId);
                                                    orderMap.put(transactionId, orderItem);
                                                }
                                                orderItem.addItem(itemName, itemQuantity, orderReady);
                                            }

                                            mOrderItems.addAll(orderMap.values());
                                            mOrderAdapter.notifyDataSetChanged();

                                            // Check if there are any orders left
                                            if (mOrderItems.isEmpty()) {
                                                // Show the "Congratulations! You have completed all your orders" text
                                                showCongratulationsText();
                                            } else {
                                                // Hide the "Congratulations!" TextView if there are orders
                                                hideCongratulationsText();
                                            }
                                        } else {
                                            Toast.makeText(KitchenLandingPage.this, "Failed to load orders.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(KitchenLandingPage.this, "Failed to load users.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
            return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            OrderItem orderItem = mOrderItems.get(position);
            holder.bind(orderItem);

            holder.checkBoxOrderStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = holder.checkBoxOrderStatus.isChecked();
                    showConfirmationDialog(orderItem, isChecked);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mOrderItems.size();
        }

        class OrderViewHolder extends RecyclerView.ViewHolder {
            private TextView textTransactionId;
            private TextView textItems;
            private CheckBox checkBoxOrderStatus;

            OrderViewHolder(@NonNull View itemView) {
                super(itemView);
                textTransactionId = itemView.findViewById(R.id.textTransactionId);
                textItems = itemView.findViewById(R.id.textItems);
                checkBoxOrderStatus = itemView.findViewById(R.id.checkBoxOrderStatus);
            }

            void bind(OrderItem orderItem) {
                textTransactionId.setText("Transaction ID: " + orderItem.getTransactionId());

                StringBuilder itemsText = new StringBuilder();
                for (OrderItem.Item item : orderItem.getItems()) {
                    itemsText.append(item.getItemName()).append(" x").append(item.getItemQuantity()).append("\n");
                }
                textItems.setText(itemsText.toString().trim());

                checkBoxOrderStatus.setChecked(orderItem.isOrderReady());
            }
        }
    }

    private void showConfirmationDialog(OrderItem orderItem, boolean isChecked) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Mark this order of Transaction ID " + orderItem.getTransactionId() + " as ready?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                orderItem.setOrderReady(isChecked);
                updateOrderStatus(orderItem.getTransactionId(), isChecked);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mOrderAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    private void updateOrderStatus(String transactionId, boolean orderReady) {
        CollectionReference usersCollection = db.collection("Users");

        for (OrderItem orderItem : mOrderItems) {
            if (orderItem.getTransactionId().equals(transactionId)) {
                CollectionReference ordersCollection = usersCollection.document(orderItem.getUserId())
                        .collection("orders");

                ordersCollection.whereEqualTo("TransactionID", transactionId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        String docId = document.getId();
                                        ordersCollection.document(docId)
                                                .update("OrderPreparedStatus", orderReady)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            if (orderReady) {
                                                                // Remove the order from the RecyclerView when confirmed
                                                                mOrderItems.remove(orderItem);
                                                                mOrderAdapter.notifyDataSetChanged();

                                                                // Check if there are any orders left
                                                                if (mOrderItems.isEmpty()) {
                                                                    // Show the "Congratulations! You have completed all your orders" text
                                                                    showCongratulationsText();
                                                                }
                                                            }
                                                            Toast.makeText(KitchenLandingPage.this, "Order status updated.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(KitchenLandingPage.this, "Failed to update order status.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                break;
            }
        }
    }

    private void showCongratulationsText() {
        // Find the "Congratulations!" TextView by its ID
        TextView congratulationsTextView = findViewById(R.id.congratulationsText);

        // Set its visibility to View.VISIBLE to make it visible
        congratulationsTextView.setVisibility(View.VISIBLE);

        // Find the ImageView by its ID
        ImageView verticalBarImageView = findViewById(R.id.verticalbar);
        // Set its visibility to View.GONE to hide it
        verticalBarImageView.setVisibility(View.GONE);
    }

    private void hideCongratulationsText() {
        // Find the "Congratulations!" TextView by its ID
        TextView congratulationsTextView = findViewById(R.id.congratulationsText);

        // Set its visibility to View.GONE to hide it
        congratulationsTextView.setVisibility(View.GONE);

        // Find the ImageView by its ID
        ImageView verticalBarImageView = findViewById(R.id.verticalbar);
        // Set its visibility to View.VISIBLE to make it visible
        verticalBarImageView.setVisibility(View.VISIBLE);
    }

    private static class OrderItem {
        private String transactionId;
        private String userId;
        private List<Item> items;
        private boolean orderReady;

        public OrderItem(String transactionId, String userId) {
            this.transactionId = transactionId;
            this.userId = userId;
            this.items = new ArrayList<>();
            this.orderReady = false;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public String getUserId() {
            return userId;
        }

        public List<Item> getItems() {
            return items;
        }

        public boolean isOrderReady() {
            return orderReady;
        }

        public void setOrderReady(boolean orderReady) {
            this.orderReady = orderReady;
        }

        public void addItem(String itemName, String itemQuantity, boolean orderReady) {
            items.add(new Item(itemName, itemQuantity));
            if (orderReady) {
                this.orderReady = true;
            }
        }

        public static class Item {
            private String itemName;
            private String itemQuantity;

            public Item(String itemName, String itemQuantity) {
                this.itemName = itemName;
                this.itemQuantity = itemQuantity;
            }

            public String getItemName() {
                return itemName;
            }

            public String getItemQuantity() {
                return itemQuantity;
            }
        }
    }
}
