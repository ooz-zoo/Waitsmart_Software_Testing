package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WalletPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WalletAdapter walletAdapter;
    private List<WalletItem> walletItemList;

    private FirebaseFirestore db;

    private FirebaseAuth mAuth;
    ImageView home, wallet, cart, profile, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_page);

        recyclerView = findViewById(R.id.mycartview);
        mAuth = FirebaseAuth.getInstance();
        home = findViewById(R.id.home);
        wallet = findViewById(R.id.wallet);
        cart = findViewById(R.id.cart);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        walletItemList = new ArrayList<>();
        walletAdapter = new WalletAdapter(this, walletItemList);
        recyclerView.setAdapter(walletAdapter);

        fetchWalletItems();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload the current activity
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
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
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        fetchWalletItems();
    }

    private void emptywallet()
    {
        Intent intentempty = new Intent(getApplicationContext(), empty_wallet.class);
        startActivity(intentempty);
        finish();
    }

    private void fetchWalletItems()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        db = FirebaseFirestore.getInstance();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            CollectionReference ordersCollection = db.collection("Users").document(userId).collection("orders");

            ordersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful())
                    {
                        List<OrderGroup> orderGroups = new ArrayList<>();

                        for (QueryDocumentSnapshot userDoc : task.getResult())
                        {

                            String transactcode = userDoc.getString("TransactionID");
                            String orderDate = userDoc.getString("Orderdate");
                            int itemtotalcost = userDoc.getLong("TotalCost").intValue();

                            OrderGroup existingOrderGroup = null;
                            for (OrderGroup orderGroup : orderGroups)
                            {
                                if (orderGroup.getTranscationCode().equals(transactcode))
                                {
                                    existingOrderGroup = orderGroup;
                                    break;
                                }
                            }

                            if (existingOrderGroup !=null)
                            {
                                existingOrderGroup.addItemTotalCost(itemtotalcost);
                            }
                            else
                            {
                                OrderGroup orderGroup = new OrderGroup(transactcode, orderDate);
                                orderGroup.addItemTotalCost(itemtotalcost);
                                orderGroups.add(orderGroup);
                            }
                        }

                        walletItemList.clear();
                        for (OrderGroup orderGroup : orderGroups)
                        {
                            int totalOrderCost = orderGroup.getTotalOrderCost();
                            WalletItem walletItem = new WalletItem(orderGroup.getTranscationCode(), orderGroup.getOrderDate(), totalOrderCost);
                            walletItemList.add(walletItem);
                        }
                        //walletItemList.addAll(walletItemListTemp);
                        walletAdapter.notifyDataSetChanged();

                        if (walletItemList.isEmpty())
                        {
                            emptywallet();
                        }
                        else {
                            //Toast.makeText(getApplicationContext(), "Failed to load order", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed to load orders.", Toast.LENGTH_SHORT).show();
                    }

                    }
            });

        }
    }
}