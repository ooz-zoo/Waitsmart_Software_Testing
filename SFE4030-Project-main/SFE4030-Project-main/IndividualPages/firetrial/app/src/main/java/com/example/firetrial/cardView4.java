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

public class cardView4 extends AppCompatActivity {

    ImageView[] images; //milkshake picture

    ImageView plusportion, backarrow, minusportion;
    Button cart;
    TextView count, totalprice;
    int countvalue = 1;
    int shake = 800;
    int total;
    TextView[] textViews; // 10 of them

    DatabaseReference[] imageRefs;
    DatabaseReference[] textRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view4);

        images = new ImageView[1];
        textViews = new TextView[9];

        imageRefs = new DatabaseReference[1];
        textRefs = new DatabaseReference[9];

        images[0] = findViewById(R.id.milkshakepicture);
        plusportion = findViewById(R.id.plusportion);
        minusportion = findViewById(R.id.minusportion);
        totalprice = findViewById(R.id.totalprice);
        backarrow = findViewById(R.id.backarrow);
        cart = findViewById(R.id.cart);
        count = findViewById(R.id.count);

        textViews[0] = findViewById(R.id.milkshake);
        textViews[1] = findViewById(R.id.kes);
        textViews[2] = findViewById(R.id.cupportion);
        textViews[3] = findViewById(R.id.timetaken);
        textViews[4] = findViewById(R.id.calories);
        textViews[5] = findViewById(R.id.starrange);
        textViews[6] = findViewById(R.id.desctext);
        textViews[7] = findViewById(R.id.textView16);
        textViews[8] = findViewById(R.id.textView5);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        imageRefs[0] = database.getReference("imageshake");

        textRefs[0] = database.getReference("textshakeitem");
        textRefs[1] = database.getReference("textshakecost");
        textRefs[2] = database.getReference("textshakeportion");
        textRefs[3] = database.getReference("textshaketimetaken");
        textRefs[4] = database.getReference("textshakecalories");
        textRefs[5] = database.getReference("textshakestar");
        textRefs[6] = database.getReference("textshakedesc");
        textRefs[7] = database.getReference("textshakenoofcups");
        textRefs[8] = database.getReference("textshaketotalcost");

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
                    total = quantity * shake;
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
                    int totalred = quantity * shake;
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
                    Toast.makeText(cardView4.this, order_placed, Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent(getApplicationContext(), cart.class);
                    startActivity(intent);
                }
            });
        }

    }
}