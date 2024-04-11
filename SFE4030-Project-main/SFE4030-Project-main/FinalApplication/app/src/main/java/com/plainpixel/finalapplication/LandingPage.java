package com.plainpixel.finalapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class LandingPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ImageView btnLogout;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseStore = FirebaseFirestore.getInstance();

    TextView greeting, item1Text, item2Text, item3Text, item4Text, item1Rating, item2Rating, item3Rating, item4Rating;
    LinearLayout item1Card, item2Card, item3Card, item4Card;
    ImageView home, wallet, cart, profile, greetingimage;

    CardView food,beverages,dessert,snacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mAuth = FirebaseAuth.getInstance();
        home = findViewById(R.id.home);
        wallet = findViewById(R.id.wallet);
        cart = findViewById(R.id.cart);
        profile = findViewById(R.id.profile);
        btnLogout = findViewById(R.id.logout);
        food = findViewById(R.id.food);
        greeting = findViewById(R.id.greeting);
        greetingimage = findViewById(R.id.greetingimage);
        beverages = findViewById(R.id.beverages);
        dessert = findViewById(R.id.dessert);
        snacks = findViewById(R.id.snacks);
        item1Text = findViewById(R.id.item1Text);
        item2Text = findViewById(R.id.item2Text);
        item3Text = findViewById(R.id.item3Text);
        item4Text = findViewById(R.id.item4Text);
        item1Rating = findViewById(R.id.item1Rating);
        item2Rating = findViewById(R.id.item2Rating);
        item3Rating = findViewById(R.id.item3Rating);
        item4Rating = findViewById(R.id.item4Rating);
        item1Card = findViewById(R.id.item1Card);
        item2Card = findViewById(R.id.item2Card);
        item3Card = findViewById(R.id.item3Card);
        item4Card = findViewById(R.id.item4Card);

        setDefaultGreeting();

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodPage.class);
                startActivity(intent);
            }
        });

        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BeveragesPage.class);
                startActivity(intent);
            }
        });

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DessertsPage.class);
                startActivity(intent);
            }
        });

        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SnacksPage.class);
                startActivity(intent);
            }
        });

        item1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = item1Text.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        item2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = item2Text.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        item3Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = item3Text.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        item4Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = item4Text.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload the current activity
                setDefaultGreeting();
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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(LandingPage.this, LoginPage.class));
                finish();
            }
        });
    }

    private void setDefaultGreeting() {
        Calendar calendar = Calendar.getInstance();

        int time = calendar.get(Calendar.HOUR_OF_DAY);

        if(time >= 7 && time <12)
        {
            greeting.setText("Good Morning");
            greetingimage.setImageResource(R.drawable.icon_sunny);
            greetingimage.setVisibility(View.VISIBLE);

        }
        else if(time >= 12 && time < 16)
        {
            greeting.setText("Good Afternoon");
            greetingimage.setImageResource(R.drawable.icon_sunny);
            greetingimage.setVisibility(View.VISIBLE);
        }
        else if(time >= 16 && time <21)
        {
            greeting.setText("Good Evening");
            greetingimage.setImageResource(R.drawable.icon_bedtime);
            greetingimage.setVisibility(View.VISIBLE);
        }
        else
        {
            greeting.setText("app under dev");
            //Intent intent = new Intent(getApplicationContext(), RestaurantClosed.class);
            //startActivity(intent);
            //finish();
        }
    }
}