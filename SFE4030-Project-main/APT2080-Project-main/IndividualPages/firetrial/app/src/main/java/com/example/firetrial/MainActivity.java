package com.example.firetrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView[] images;
    TextView[] textViews;
    RatingBar[] ratingBars;
    TextView[] ratingTexts;

    DatabaseReference[] imageRefs;
    DatabaseReference[] textRefs;
    DatabaseReference[] ratingRefs;
    DatabaseReference[] ratingTextRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new ImageView[4];
        textViews = new TextView[7];
        ratingBars = new RatingBar[4];
        ratingTexts = new TextView[4];
        imageRefs = new DatabaseReference[4];
        textRefs = new DatabaseReference[7];
        ratingRefs = new DatabaseReference[4];
        ratingTextRefs = new DatabaseReference[4];

        images[0] = findViewById(R.id.images);
        images[1] = findViewById(R.id.latte);
        images[2] = findViewById(R.id.smoothie);
        images[3] = findViewById(R.id.shake);

        textViews[0] = findViewById(R.id.textone); //this is mojitotext
        textViews[1] = findViewById(R.id.firsttext);
        textViews[2] = findViewById(R.id.secondtext);
        textViews[3] = findViewById(R.id.thirdtext);
        textViews[4] = findViewById(R.id.lattetext);
        textViews[5] = findViewById(R.id.smoothietext);
        textViews[6] = findViewById(R.id.shaketext);


        ratingBars[0] = findViewById(R.id.ratingBar);
        ratingBars[1] = findViewById(R.id.latteratingBar);
        ratingBars[2] = findViewById(R.id.smoothieratingBar);
        ratingBars[3] = findViewById(R.id.shakeratingBar);

        ratingTexts[0] = findViewById(R.id.ratingtext);
        ratingTexts[1] = findViewById(R.id.latteratingtext);
        ratingTexts[2] = findViewById(R.id.smoothieratingtext);
        ratingTexts[3] = findViewById(R.id.shakeratingtext);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        imageRefs[0] = database.getReference("image");
        imageRefs[1] = database.getReference("imagetwo");
        imageRefs[2] = database.getReference("imagethree");
        imageRefs[3] = database.getReference("imagefour");

        textRefs[0] = database.getReference("text");
        textRefs[1] = database.getReference("textuno");
        textRefs[2] = database.getReference("texttwo");
        textRefs[3] = database.getReference("textthree");
        textRefs[4] = database.getReference("textcoffee");
        textRefs[5] = database.getReference("textsmoothie");
        textRefs[6] = database.getReference("textshake");

        ratingRefs[0] = database.getReference("rating");
        ratingRefs[1] = database.getReference("ratingtwo");
        ratingRefs[2] = database.getReference("ratingthree");
        ratingRefs[3] = database.getReference("ratingfour");

        ratingTextRefs[0] = database.getReference("ratetextone");
        ratingTextRefs[1] = database.getReference("latteratetextone");
        ratingTextRefs[2] = database.getReference("smoothieratetextone");
        ratingTextRefs[3] = database.getReference("shakeratetextone");



// Set click listener for the images

        images[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentzero = new Intent(MainActivity.this, cardView.class);
                startActivity(intentzero);
            }
        });
        images[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cardView2.class);
                startActivity(intent);
            }
        });

        images[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentone = new Intent(MainActivity.this, cardView3.class);
                startActivity(intentone);
            }
        });

        images[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttwo = new Intent(MainActivity.this, cardView4.class);
                startActivity(intenttwo);
            }
        });





// Iterate over images
        for (int i = 0; i < images.length; i++) {
            int position = i;
            imageRefs[i].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    Picasso.get().load(value).into(images[position]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });
        }

        // Iterate over textViews
        for (int i = 0; i < textViews.length; i++) {
            int finalI = i;
            textRefs[i].addValueEventListener(new ValueEventListener() {
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
        }


// Iterate over ratingBars
        for (int i = 0; i < ratingBars.length; i++) {
            int finalI = i;
            ratingRefs[i].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Float value = dataSnapshot.getValue(Float.class);
                    if (value != null) {
                        ratingBars[finalI].setRating(value);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });
        }

// Iterate over ratingTexts
        for (int i = 0; i < ratingTexts.length; i++) {
            int finalI = i;
            ratingTextRefs[i].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Double value = dataSnapshot.getValue(Double.class);
                    if (value != null) {
                        String ratingValue = String.valueOf(value);
                        ratingTexts[finalI].setText(ratingValue);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });
        }

    }
}