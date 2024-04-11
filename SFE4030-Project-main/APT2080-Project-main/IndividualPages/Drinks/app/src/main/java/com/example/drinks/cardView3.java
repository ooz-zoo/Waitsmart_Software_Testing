package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class cardView3 extends AppCompatActivity {

    ImageView back, minus, plus;
    Button cart;
    TextView count, totalprice;
    int countvalue = 1;
    int smoothie = 750;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view3);

        back = findViewById(R.id.back);
        cart = findViewById(R.id.cart);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        count = findViewById(R.id.count);
        totalprice = findViewById(R.id.totalprice);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countvalue++;
                count.setText("" + countvalue);
                int quantity = Integer.parseInt(count.getText().toString());
                total = quantity * smoothie;
                totalprice.setText("Kes" + "" + total);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
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
                int totalred = quantity * smoothie;
                // Subtract 650 for every quantity reduction except the first one

                totalprice.setText("Kes" + totalred);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(cardView3.this, order_placed, Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent(getApplicationContext(), cart.class);
                startActivity(intent);
            }
        });
    }
}