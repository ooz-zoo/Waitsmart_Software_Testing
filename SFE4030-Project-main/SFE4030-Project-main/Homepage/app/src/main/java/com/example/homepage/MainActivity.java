package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView greeting;

    ImageView greetingimage, home, wallet, profile,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greeting = findViewById(R.id.greeting);
        greetingimage = findViewById(R.id.greetingimage);
        home = findViewById(R.id.home);
        wallet = findViewById(R.id.wallet);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload the current activity
                setDefaultGreeting();
            }
        });

        setDefaultGreeting();

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttwo = new Intent(getApplicationContext(), wallet.class);
                startActivity(intenttwo);
            }
        });

    }

    private void setDefaultGreeting() {
        Calendar calendar = Calendar.getInstance();

        int time = calendar.get(Calendar.HOUR_OF_DAY);

        if(time >= 0 && time <16)
        {
            greeting.setText("Good Morning");
            greetingimage.setImageResource(R.drawable.morning);
            greetingimage.setVisibility(View.VISIBLE);

        }
        else if(time >= 12 && time < 16)
        {
            greeting.setText("Good Afternoon");
            greetingimage.setImageResource(R.drawable.sunny);
            greetingimage.setVisibility(View.VISIBLE);
        }
        else if(time >= 16 && time <21)
        {
            greeting.setText("Good Evening");
            greetingimage.setImageResource(R.drawable.bedtime);
            greetingimage.setVisibility(View.VISIBLE);
        }
        else
        {
            greeting.setText("Hello Mate");
            greetingimage.setVisibility(View.GONE);
        }

    }
}