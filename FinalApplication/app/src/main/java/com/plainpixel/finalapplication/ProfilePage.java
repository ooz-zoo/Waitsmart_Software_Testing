package com.plainpixel.finalapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class ProfilePage extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String uid;
    TextView fname_main, fname, email, contact, balance, position;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        fname_main = findViewById(R.id.fname_main);
        fname = findViewById(R.id.fname);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        balance = findViewById(R.id.balance);
        position = findViewById(R.id.position);
        backarrow = findViewById(R.id.back_arrow);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fstore.collection("Users").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fname_main.setText(value.getString("FullName"));
                fname.setText(value.getString("FullName"));
                email.setText(value.getString("ContactEmail"));
                contact.setText(value.getString("ContactNumber"));
                balance.setText(value.getString("Balance"));

                if (value.getString("level").equals("user")){
                    position.setText("User");
                } else if (value.getString("level").equals("kitchen")) {
                    position.setText("Kitchen");
                }else if (value.getString("level").equals("cashier")) {
                    position.setText("Cashier");
                }

            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}