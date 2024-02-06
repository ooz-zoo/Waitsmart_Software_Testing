package com.plainpixel.finalapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Context context;
    String uid, level;
    Handler h = new Handler();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected()){
            startActivity(new Intent(MainActivity.this, NoInternetPage.class));
        } else{
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (fAuth.getCurrentUser()!=null){
                        uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                        DocumentReference documentReference = fStore.collection("Users").document(uid);

                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.getString("level").equals("user")){
                                    startActivity(new Intent(MainActivity.this, LandingPage.class));
                                    finish();
                                } else if (documentSnapshot.getString("level").equals("kitchen")) {
                                    startActivity(new Intent(MainActivity.this, KitchenLandingPage.class));
                                    finish();
                                } else if (documentSnapshot.getString("level").equals("cashier")) {
                                    startActivity(new Intent(MainActivity.this, CashierLandingPage.class));
                                    finish();
                                }
                            }
                        });
                    } else {
                        startActivity(new Intent(MainActivity.this, LoginPage.class));
                    }
                    finish();
                }
            }, 2000);
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetwork() != null;
    }
}