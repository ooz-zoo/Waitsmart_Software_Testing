package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Cart extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener {

    private RecyclerView mycartview;
    private CartAdapter cartAdapter;
    private List<Cartitem> cartitems = new ArrayList<>();
    private TextView TotalKes;

    boolean isOrderPlaced = false;


    ImageView arrowback;

    AppCompatButton continueshoppingbutton, confirmorderbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Initialize the RecyclerView and its layout manager
        mycartview = findViewById(R.id.mycartview);
        TotalKes = findViewById(R.id.TotalKes);

        //set layoutmanager for recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mycartview.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue().toString();

            if (key.startsWith("itemName_"))
            {
                String uniqueKey = key.replace("itemName_", "");

                String itemName = sharedPreferences.getString("itemName_" + uniqueKey, "");
                String itemPrice = sharedPreferences.getString("itemPrice_" + uniqueKey,"");
                String itemImage = sharedPreferences.getString("itemImage_" +uniqueKey, "");
                String itemquantity = sharedPreferences.getString("itemquantity_" +uniqueKey,"");
                int totalcost = sharedPreferences.getInt("itemtotalcost_" + uniqueKey,0);
                String timetaken = sharedPreferences.getString("itemtimetaken_" + uniqueKey, "");

                //Create a cartitem object
                Cartitem cartitem = new Cartitem(itemName, itemPrice, itemImage, itemquantity, totalcost, timetaken);

                //add the Cartitem to the cartitems list
                cartitems.add(cartitem);
            }
        }

        if (cartitems.isEmpty())
        {
            Intent intentempty = new Intent(getApplicationContext(), empty_cart.class);
            startActivity(intentempty);
            finish();
        }

        //Intent resultintent = new Intent();
        //setResult(RESULT_OK, resultintent);
        //finish();

        arrowback = findViewById(R.id.arrowback);
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // goes back to previous activity
            }
        });

        confirmorderbutton = findViewById(R.id.confirmorderbutton);
        continueshoppingbutton = findViewById(R.id.continueshoppingbutton);

        continueshoppingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                startActivity(intent);
                finish();
            }
        });

        confirmorderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseFirestore ordersdb = FirebaseFirestore.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                String userid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = ordersdb.collection("Users").document(userid);

                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String userName = documentSnapshot.getString("ContactEmail");

                            //make orders sub-collection under users collection
                            CollectionReference orderscollection = ordersdb.collection("Users").document(userid).collection("orders");

                            // List<String> itemList = new ArrayList<>();
                            //List<Integer> quantityList = new ArrayList<>();
                            AtomicInteger totalcost = new AtomicInteger(0);

                            //final AtomicLong totalPreparationTimeInMillis = new AtomicLong(0);

                            // Get the current date
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String currentDate = dateFormat.format(calendar.getTime());

                            // Get the current time
                            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                            String currentTime = timeFormat.format(calendar.getTime());

                            long totalPreparationTimeInMillis = 0;
                            for (Cartitem cartitem : cartitems) {
                                String timetaken = cartitem.getTimetaken();
                                if (timetaken != null && !timetaken.isEmpty()) {

                                    int preparation_time_in_min = Integer.parseInt(timetaken.replaceAll("\\D+", ""));
                                    totalPreparationTimeInMillis += preparation_time_in_min * 60 * 1000;

                                }
                            }

                            long currentTimeMillis = System.currentTimeMillis();
                            long estimatedPreparationTimeInMillis = currentTimeMillis + totalPreparationTimeInMillis;

                            SimpleDateFormat timeFormattwo = new SimpleDateFormat("h:mm a");
                            String estimatedpreparationtime = timeFormattwo.format(new Date(estimatedPreparationTimeInMillis));

                            boolean isInsufficientBalance = false;
                            String transactionID = generateTransactionID();

                            for (Cartitem cartitem : cartitems) {
                                //  itemList.add(cartitem.getItemName());
                                //quantityList.add(Integer.parseInt(cartitem.getQuantity()));
                                int totalCost = cartitem.getTotalcost();


                                String timetaken = cartitem.getTimetaken();
                                String itemName = cartitem.getItemName();
                                String itemPrice = cartitem.getItemPrice();
                                //String itemImage = cartitem.getItemImage();
                                String itemquantity = cartitem.getQuantity();

                                int totalitemCost = calculateTotalCost();
                                String userBalanceString = documentSnapshot.getString("Balance");
                                int userbalance = Integer.parseInt(userBalanceString);

                                if (userbalance >= totalitemCost) {
                                    //String transactionID = generateTransactionID();

                                    //send the data to firestore
                                    Map<String, Object> orderData = new HashMap<>();
                                    orderData.put("TransactionID", transactionID);
                                    orderData.put("ItemName", itemName);
                                    orderData.put("ItemPrice", itemPrice);
                                    // orderData.put("itemImage", itemImage);
                                    orderData.put("ItemQuantity", itemquantity);
                                    orderData.put("TotalCost", totalCost);
                                    orderData.put("Orderdate", currentDate);
                                    orderData.put("Ordertime", currentTime);
                                    orderData.put("Username", userName);
                                    orderData.put("OrderPreparedStatus", false);
                                    orderData.put("Orderreadytime", estimatedpreparationtime);

                                    int newBalance = userbalance - totalitemCost;
                                    String newBalanceString = String.valueOf(newBalance);
                                    documentReference.update("Balance", newBalanceString);

                                    orderscollection.add(orderData)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {

                                                    Toast.makeText(getApplicationContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();

                                                    cartitems.clear();
                                                    cartAdapter.notifyDataSetChanged();
                                                    totalcost.addAndGet(totalCost);
                                                    SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
                                                    sharedPreferences.edit().clear().apply();

                                                    isOrderPlaced = true;

                                                    Intent intenttwo = new Intent(getApplicationContext(), Confirmed_order.class);
                                                    startActivity(intenttwo);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                                                    confirmorderbutton.setEnabled(true);
                                                }
                                            });
                                } else {
                                    isInsufficientBalance = true;
                                    break;
                                }
                            }
                                if (isInsufficientBalance)
                                {
                                    new AlertDialog.Builder(Cart.this)
                                            .setTitle("Insufficient Balance")
                                            .setMessage("You dont have enough funds to place this order")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                    confirmorderbutton.setEnabled(true);
                                }
                            }

                        }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                confirmorderbutton.setEnabled(false);
            }
        });

        //create an instance of CartAdapter and pass the cartitems list
        cartAdapter = new CartAdapter(cartitems, this, this, TotalKes);

        //set the adapter to the mycartview RecyclerView
        mycartview.setAdapter(cartAdapter);
       // totalCost = 0;
        calculateTotalCost();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (isOrderPlaced)
        {
            Intent intenttwo = new Intent(getApplicationContext(), Confirmed_order.class);
            startActivity(intenttwo);
        }
    }

    @Override
    public void onQuantityChanged() {
        calculateTotalCost();
    }
    private int calculateTotalCost() {
        int totalCost = 0;
        for (Cartitem cartItem : cartitems)
        {
            totalCost += cartItem.getTotalcost();
        }

        // Display total cost in TextView
        TextView TotalKes = findViewById(R.id.TotalKes);
        TotalKes.setText("Kes" + " " + " " + totalCost);

        return totalCost;

    }

    private String generateTransactionID() {

        long timestamp = System.currentTimeMillis();

        int randomNumber = new Random().nextInt(900000) + 100000;

        String combinedId = timestamp + "_" + randomNumber;

        String hashedId = generateHash(combinedId);

        String transactionId = hashedId.substring(0, 13);

        return transactionId;
    }

    private String generateHash(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hashText = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hashText.append('0');
                }
                hashText.append(hex);
            }
            return hashText.toString().toUpperCase();
        }

        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}