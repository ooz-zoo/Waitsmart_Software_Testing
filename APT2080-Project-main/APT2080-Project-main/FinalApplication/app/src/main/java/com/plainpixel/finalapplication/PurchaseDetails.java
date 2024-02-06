package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseDetails extends AppCompatActivity {

    private String transactionId;

    private FirebaseFirestore db;

    private RecyclerView recyclerView;
    private PurchaseDetails_Adapater purchaseDetails_adapater;

    private List<List<PurchaseItem>> groupedPurchaseItemList;

    private FirebaseAuth mAuth;

    ImageView home, wallet, cart, profile, logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);

        home = findViewById(R.id.home);
        wallet = findViewById(R.id.wallet);
        cart = findViewById(R.id.cart);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload the current activity
                Intent intent = new Intent(getApplicationContext(), PurchaseDetails.class);
                startActivity(intent);
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WalletPage.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(PurchaseDetails.this, LoginPage.class));
                finish();
            }
        });



        Intent intent = getIntent();
        if (intent != null)
        {
            transactionId = intent.getStringExtra("transactionId");
        }

        if (transactionId != null)
        {
            fetchItemDetails(transactionId);
        }
        else {
            Toast.makeText(getApplicationContext(), "Could not retrieve order details", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchItemDetails(String transactionId)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();
        String userId = currentUser.getUid();
        CollectionReference ordersCollection = db.collection("Users").document(userId).collection("orders");

        Query query = ordersCollection.whereEqualTo("TransactionID", transactionId);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, List<PurchaseItem>> groupedItems = new HashMap<>();
                    //purchaseItemList = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String itemName = document.getString("ItemName");
                        String itemCost = document.getString("ItemPrice");
                        String itemqnty = document.getString("ItemQuantity");
                        //boolean isOrderReady = document.getBoolean("OrderPreparedStatus");

                        PurchaseItem purchaseItem = new PurchaseItem(itemName, itemCost, itemqnty);
                        //purchaseItem.setOrderReady(isOrderReady);

                        String itemTransactionId = document.getString("TransactionID");

                        if (groupedItems.containsKey(itemTransactionId)) {
                            groupedItems.get(itemTransactionId).add(purchaseItem);
                        } else {
                            List<PurchaseItem> itemList = new ArrayList<>();
                            itemList.add(purchaseItem);
                            groupedItems.put(itemTransactionId, itemList);
                        }
                        //purchaseItemList.add(purchaseItem);
                    }

                    List<PurchaseItem> purchaseItemList = groupedItems.get(transactionId);
                    if (purchaseItemList != null && !purchaseItemList.isEmpty()) {
                        showPurchaseDetails(purchaseItemList);
                    } else {
                        Toast.makeText(getApplicationContext(), "No items found for this ID", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch item details", Toast.LENGTH_SHORT).show();
                }

                // purchaseDetails_adapater.notifyDataSetChanged();
            }
        });
    }

    private void showPurchaseDetails(List<PurchaseItem> purchaseItemList)
    {
        recyclerView = findViewById(R.id.purchaseDetailsview);
        purchaseDetails_adapater = new PurchaseDetails_Adapater(purchaseItemList);

        recyclerView.setAdapter(purchaseDetails_adapater);
        recyclerView.setLayoutManager(new LinearLayoutManager(PurchaseDetails.this));
    }
}