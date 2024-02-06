package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Confirmed_order extends AppCompatActivity {

    TextView username, day, month, year, hour, minute, timeline ;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrimed_order);

        username = findViewById(R.id.username);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        //hour = findViewById(R.id.hour);
        //minute = findViewById(R.id.minute);
        timeline = findViewById(R.id.timeline);
        back = findViewById(R.id.back);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore ConfirmOrder = FirebaseFirestore.getInstance();

        String userid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = ConfirmOrder.collection("Users").document(userid);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot userDocument = task.getResult();

                    if (userDocument.exists())
                    {
                        String usernameVal = userDocument.getString("FullName");
                        username.setText(usernameVal);

                        //String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

                        //fetch the order details from the orders subcollection
                        ConfirmOrder.collection("Users").document(userid).collection("orders")
                                //.orderBy("Orderdate", Query.Direction.DESCENDING)
                                .orderBy("Ordertime", Query.Direction.DESCENDING)
                                //.limit(1)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful())
                                        {
                                            QuerySnapshot querySnapshot = task.getResult();

                                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                                DocumentSnapshot orderDocument = querySnapshot.getDocuments().get(0);

                                                String dateString = orderDocument.getString("Orderdate");
                                                String timeString = orderDocument.getString("Orderreadytime");

                                                //parse and format the date

                                                if (dateString != null)
                                                {
                                                    String[] dateParts = dateString.split("-");
                                                    String yearvalue = dateParts[0];
                                                    String monthvalue = dateParts[1];
                                                    String dayvalue = dateParts[2];

                                                    String monthString = getMonthString(monthvalue);

                                                    day.setText(dayvalue);
                                                    month.setText(monthString);
                                                    year.setText(yearvalue);
                                                }

                                                timeline.setText(timeString);

                                               // if (timeString != null)
                                                //{
                                                    /**String[] timeParts = timeString.split(" ");
                                                    String[] clockParts = timeParts[0].split(":");

                                                    String hourvalue = clockParts[0];
                                                    String minutevalue = clockParts[1];
                                                    String timelinevalue = timeParts[1];

                                                    hour.setText(hourvalue);
                                                    minute.setText(minutevalue);
                                                    timeline.setText(timelinevalue);  //Keep AM PM **/
                                               // }
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "Error in retrieving records", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Performing task error", Toast.LENGTH_SHORT).show();
                                           // Log.e("FirestoreError", "Error fetching user data: " + task.getException().getMessage());
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WalletPage.class);
                startActivity(intent);
            }
        });
    }

    private String getMonthString(String monthValue)
    {
        int monthNumber = Integer.parseInt(monthValue);

        String[] monthNames = new String[]
                {
                        "", "January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"
                };
        return  monthNames[monthNumber];
    }

}