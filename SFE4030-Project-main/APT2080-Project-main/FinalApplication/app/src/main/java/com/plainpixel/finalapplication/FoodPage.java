package com.plainpixel.finalapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class  FoodPage extends AppCompatActivity {
    FirebaseFirestore fstore;
    TextView text_one, text_two, text_three, text_four, text_five, ratingText_one, ratingText_two, ratingText_three, ratingText_four, ratingText_five;
    RatingBar ratingBar_one, ratingBar_two, ratingBar_three, ratingBar_four, ratingBar_five;
    ImageView image1, image2, image3, image4, image5, home, wallet, shoppingcart, profile;
    private ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);

        text_one = findViewById(R.id.text_one);
        text_two = findViewById(R.id.text_two);
        text_three = findViewById(R.id.text_three);
        text_four = findViewById(R.id.text_four);
        text_five = findViewById(R.id.text_five);
        ratingText_one = findViewById(R.id.ratingText_one);
        ratingText_two = findViewById(R.id.ratingText_two);
        ratingText_three = findViewById(R.id.ratingText_three);
        ratingText_four = findViewById(R.id.ratingText_four);
        ratingText_five = findViewById(R.id.ratingText_five);
        ratingBar_one = findViewById(R.id.ratingBar_one);
        ratingBar_two = findViewById(R.id.ratingBar_two);
        ratingBar_three = findViewById(R.id.ratingBar_three);
        ratingBar_four = findViewById(R.id.ratingBar_four);
        ratingBar_five = findViewById(R.id.ratingBar_five);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        home = findViewById(R.id.home);
        wallet = findViewById(R.id.wallet);
        shoppingcart = findViewById(R.id.shoppingcart);
        profile = findViewById(R.id.profile);
        btnLogout = findViewById(R.id.logout);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = text_one.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = text_two.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = text_three.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = text_four.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = text_five.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DetailedItemPage.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        shoppingcart.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });

        fstore = FirebaseFirestore.getInstance();

        String itemName1 = text_one.getText().toString();
        String itemName2 = text_two.getText().toString();
        String itemName3 = text_three.getText().toString();
        String itemName4 = text_four.getText().toString();
        String itemName5 = text_five.getText().toString();

        DocumentReference documentReference1 = fstore.collection("Menu").document(itemName1);
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                text_one.setText(value1.getString("item_name"));
                ratingBar_one.setRating(Float.parseFloat(value1.getString("rating")));
                ratingText_one.setText(value1.getString("rating"));
                String url = value1.getString("img");
                Picasso.get().load(url).into(image1);
            }
        });

        DocumentReference documentReference2 = fstore.collection("Menu").document(itemName2);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                text_two.setText(value.getString("item_name"));
                ratingBar_two.setRating(Float.parseFloat(value.getString("rating")));
                ratingText_two.setText(value.getString("rating"));
                String url = value.getString("img");
                Picasso.get().load(url).into(image2);
            }
        });

        DocumentReference documentReference3 = fstore.collection("Menu").document(itemName3);
        documentReference3.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                text_three.setText(value.getString("item_name"));
                ratingBar_three.setRating(Float.parseFloat(value.getString("rating")));
                ratingText_three.setText(value.getString("rating"));
                String url = value.getString("img");
                Picasso.get().load(url).into(image3);
            }
        });

        DocumentReference documentReference4 = fstore.collection("Menu").document(itemName4);
        documentReference4.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                text_four.setText(value.getString("item_name"));
                ratingBar_four.setRating(Float.parseFloat(value.getString("rating")));
                ratingText_four.setText(value.getString("rating"));
                String url = value.getString("img");
                Picasso.get().load(url).into(image4);
            }
        });

        DocumentReference documentReference5 = fstore.collection("Menu").document(itemName5);
        documentReference5.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                text_five.setText(value.getString("item_name"));
                ratingBar_five.setRating(Float.parseFloat(value.getString("rating")));
                ratingText_five.setText(value.getString("rating"));
                String url = value.getString("img");
                Picasso.get().load(url).into(image5);
            }
        });
    }
}