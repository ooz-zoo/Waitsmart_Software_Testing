package com.example.firetrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class cardView2 extends AppCompatActivity {

    ImageView[] images; //just one the latte picture

    ImageView plusportion, backarrow, minusportion;
    Button cart;
    TextView count, totalprice;
    int countvalue = 1;
    int latte = 650;
    int total;
    TextView[] textViews; // 10 of them

    DatabaseReference[] imageRefs;
    DatabaseReference[] textRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view2);

        images = new ImageView[1];
        textViews = new TextView[9];

        imageRefs = new DatabaseReference[1];
        textRefs = new DatabaseReference[9];

        images[0] = findViewById(R.id.lattepicture);
        plusportion = findViewById(R.id.plusportion);
        minusportion = findViewById(R.id.minusportion);
        totalprice = findViewById(R.id.totalprice);
        backarrow = findViewById(R.id.backarrow);
        cart = findViewById(R.id.cart);
        count = findViewById(R.id.count);

        textViews[0] = findViewById(R.id.latte);
        textViews[1] = findViewById(R.id.kes);
        textViews[2] = findViewById(R.id.cupportion);
        textViews[3] = findViewById(R.id.timetaken);
        textViews[4] = findViewById(R.id.calories);
        textViews[5] = findViewById(R.id.starrange);
        textViews[6] = findViewById(R.id.desctext);
        textViews[7] = findViewById(R.id.textView16);
        textViews[8] = findViewById(R.id.textView5);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        imageRefs[0] = database.getReference("imagelatte");

        textRefs[0] = database.getReference("textlatte");
        textRefs[1] = database.getReference("textlattekes");
        textRefs[2] = database.getReference("textlatteportion");
        textRefs[3] = database.getReference("texttimetaken");
        textRefs[4] = database.getReference("textlattecalories");
        textRefs[5] = database.getReference("textlattestar");
        textRefs[6] = database.getReference("textlattedesc");
        textRefs[7] = database.getReference("textlattenoofcups");
        textRefs[8] = database.getReference("textlattetotalcost");


        //ITERATE Over the images
        for (int k = 0; k < images.length; k++) {
            int finalI = k;
            imageRefs[k].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    Picasso.get().load(value).into(images[finalI]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });
        }

        // Iterate over textViews
        for (int k = 0; k < textViews.length; k++) {
            int finalI = k;
            textRefs[k].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    textViews[finalI].setText(value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });

            plusportion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countvalue++;
                    count.setText("" + countvalue);
                    int quantity = Integer.parseInt(count.getText().toString());
                    total = quantity * latte;
                    totalprice.setText("Kes" + "" + total);
                }
            });

          minusportion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(countvalue <= 1)
                    {countvalue = 1;

                    }
                    else
                    {
                        countvalue--;
                    }
                    count.setText("" + countvalue);

                    int quantity = Integer.parseInt(count.getText().toString());
                    int totalred = quantity * latte;
                    // Subtract 650 for every quantity reduction except the first one

                    totalprice.setText("Kes" + totalred);

                }
            });

            backarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String order_placed = "Item Added to cart";
                    Toast.makeText(cardView2.this, order_placed, Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent(getApplicationContext(), cart.class);
                    startActivity(intent);
                }
            });
        }

    }
}