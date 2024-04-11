package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CardView cardView, cardView2, cardView3, cardView4;
    //View imageView;
    //TextView textView, textView2, textView3, textView4,textView5;
    //SearchView searchView;

    //Animation anim_from_bottom, anim_from_top, anim_from_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentone = new Intent(getApplicationContext(), sec.class);
                startActivity(intentone);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttwo = new Intent(getApplicationContext(), cardView2.class);
                startActivity(intenttwo);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentthree = new Intent(getApplicationContext(), cardView3.class);
                startActivity(intentthree);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfour = new Intent(getApplicationContext(), cardView4.class);
                startActivity(intentfour);
            }
        });

    }
}