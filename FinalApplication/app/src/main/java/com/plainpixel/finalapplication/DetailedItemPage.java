package com.plainpixel.finalapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailedItemPage extends AppCompatActivity {
    FirebaseFirestore fstore;
    TextView item_title, cost, timetaken, calories, stars, desctext,type;
    ImageView plusportion, backarrow, minusportion, item_picture, shoppingcart;
    TextView count, totalprice;
    int countvalue = 1;
    int total;
    Button cart;
    private List<Cartitem> cartitems = new ArrayList<>();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item_page);

        item_title = findViewById(R.id.item_title);
        cost = findViewById(R.id.cost);
        timetaken = findViewById(R.id.timetaken);
        calories = findViewById(R.id.calories);
        stars = findViewById(R.id.stars);
        desctext = findViewById(R.id.desctext);
        plusportion = findViewById(R.id.plusportion);
        minusportion = findViewById(R.id.minusportion);
        totalprice = findViewById(R.id.totalprice);
        backarrow = findViewById(R.id.back_arrow);
        count = findViewById(R.id.count);
        cart = findViewById(R.id.cart);
        item_picture = findViewById(R.id.item_picture);
        shoppingcart = findViewById(R.id.shoppingcart);
        type = findViewById(R.id.type);


        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");

        fstore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fstore.collection("Menu").document(itemName);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                item_title.setText(value1.getString("item_name"));
                cost.setText(value1.getString("cost"));
                timetaken.setText(value1.getString("time"));
                calories.setText(value1.getString("calories"));
                stars.setText(value1.getString("rating"));
                desctext.setText(value1.getString("description"));
                type.setText(value1.getString("type"));
                totalprice.setText("Ksh " + value1.getString("cost"));
                url = value1.getString("img");
                Picasso.get().load(url).into(item_picture);
            }
        });

        plusportion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countvalue++;
                count.setText("" + countvalue);
                int quantity = Integer.parseInt(count.getText().toString());
                total = quantity * Integer.parseInt(cost.getText().toString());
                totalprice.setText("Ksh " + "" + total);
            }
        });

        minusportion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countvalue <= 1) {
                    countvalue = 1;
                }
                else {
                    countvalue--;
                }
                count.setText("" + countvalue);
                int quantity = Integer.parseInt(count.getText().toString());
                total = quantity * Integer.parseInt(cost.getText().toString());
                totalprice.setText("Ksh " + "" + total);
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String itemName = item_title.getText().toString();
                String itemPrice = cost.getText().toString();
                String quantity = count.getText().toString();
                String time = timetaken.getText().toString();

                //String totalCostString = totalprice.getText().toString().replace("Ksh", "").trim();

                int price = Integer.parseInt(itemPrice);
                int itemquantity = Integer.parseInt(quantity);
                int totalCost = price * itemquantity;

                if (isItemInCart(itemName))
                {
                    Toast.makeText(getApplicationContext(), "Item is already in Cart", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String uniquekey = itemName + "_" + System.currentTimeMillis();

                    editor.putString("itemName_" + uniquekey, itemName);
                    editor.putString("itemPrice_" + uniquekey, itemPrice);
                    editor.putString("itemImage_" + uniquekey, url);
                    editor.putString("itemquantity_" +uniquekey, quantity);
                    editor.putInt("itemtotalcost_" + uniquekey, totalCost);
                    editor.putString("itemtimetaken_" + uniquekey, time);

                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
            }
        });
    }

    private boolean isItemInCart(String itemName)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet())
        {
            String key = entry.getKey();
            if (key.startsWith("itemName_"))
            {
                String uniqueKey = key.replace("itemName_", "");
                String cartItemName = sharedPreferences.getString("itemName_" + uniqueKey, "");
                if (cartItemName.equals(itemName))
                {
                    return true;
                }
            }
        }
        return false;
    }
}