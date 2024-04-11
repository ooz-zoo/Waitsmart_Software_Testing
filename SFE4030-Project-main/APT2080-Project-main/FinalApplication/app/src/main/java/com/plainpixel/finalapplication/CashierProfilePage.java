package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CashierProfilePage extends AppCompatActivity {

    TextView fname_main, fname, email, contact, balance, position;
    ImageView backarrow;
    EditText newBalanceInput;
    Button updateBalanceButton;
    FirebaseFirestore firestore;
    FirebaseAuth fAuth;

    RadioButton radioUser, radioCashier, radioKitchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_profile_page);

        fname_main = findViewById(R.id.fname_main);
        fname = findViewById(R.id.fname);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        balance = findViewById(R.id.balance);
        position = findViewById(R.id.position);
        backarrow = findViewById(R.id.back_arrow);

        newBalanceInput = findViewById(R.id.new_balance_input);
        updateBalanceButton = findViewById(R.id.update_balance_button);
        firestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        // Get the user's information passed from MyAdapter
        String fullName = getIntent().getStringExtra("user_full_name");
        String userEmail = getIntent().getStringExtra("user_email");
        String userContact = getIntent().getStringExtra("user_contact");
        String userBalance = getIntent().getStringExtra("user_balance");
        String userLevel = getIntent().getStringExtra("user_level"); // Get the user's level

        // Set the TextViews with the user's information
        fname_main.setText(fullName);
        fname.setText(fullName);
        email.setText(userEmail);
        contact.setText(userContact);
        balance.setText(userBalance);
        position.setText(getPositionText(userLevel)); // Set the user's position in the TextView

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize the radio buttons and set a default level when the activity starts
        radioUser = findViewById(R.id.radio_user);
        radioCashier = findViewById(R.id.radio_cashier);
        radioKitchen = findViewById(R.id.radio_kitchen);
        setRadioCheckedState(userLevel);

        // Set an OnCheckedChangeListener for the radio group to handle level changes
        RadioGroup radioGroupLevel = findViewById(R.id.radio_group_level);
        radioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String selectedLevel;
                if (checkedId == R.id.radio_cashier) {
                    selectedLevel = "cashier";
                } else if (checkedId == R.id.radio_kitchen) {
                    selectedLevel = "kitchen";
                } else {
                    selectedLevel = "user"; // Default level
                }

                // Pass the selected level to the method and update it in Firestore
                setLevelInFirestore(selectedLevel);
            }
        });

        // Set an OnClickListener for the update balance button
        updateBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBalance(userBalance);
            }
        });
    }

    private void setRadioCheckedState(String userLevel) {
        switch (userLevel) {
            case "cashier":
                radioCashier.setChecked(true);
                break;
            case "kitchen":
                radioKitchen.setChecked(true);
                break;
            default:
                radioUser.setChecked(true);
                break;
        }
    }

    private void setLevelInFirestore(String selectedLevel) {
        // Ensure uid is not null
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            // Get the user ID
            String uid = currentUser.getUid();

            // Create a Map to hold the updated level field
            Map<String, Object> levelUpdate = new HashMap<>();
            levelUpdate.put("level", selectedLevel);

            // Update the Firestore document with the new level value
            firestore.collection("Users").document(uid) // Use the uid as the document ID
                    .update(levelUpdate)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Update the position TextView with the new level text
                            position.setText(getPositionText(selectedLevel));
                            Toast.makeText(CashierProfilePage.this, "Level Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // An error occurred while updating the level in Firestore
                            Toast.makeText(CashierProfilePage.this, "Failed to Update Level", Toast.LENGTH_SHORT).show();
                            Log.e("Firestore", "Error updating level", e);
                        }
                    });
        } else {
            Toast.makeText(CashierProfilePage.this, "User is not authenticated.", Toast.LENGTH_SHORT).show();
            Log.e("Authentication", "User is not authenticated.");
        }
    }

    private void updateBalance(String currentBalance) {
        String newBalanceStr = newBalanceInput.getText().toString().trim();

        if (TextUtils.isEmpty(newBalanceStr)) {
            Toast.makeText(this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
            return;
        }

        double newBalance = Double.parseDouble(newBalanceStr);
        double currentBalanceValue = Double.parseDouble(currentBalance);

        double updatedBalance = currentBalanceValue + newBalance;
        String updatedBalanceStr = String.valueOf(updatedBalance);

        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference documentReference = firestore.collection("Users").document(uid);

            Map<String, Object> balanceUpdate = new HashMap<>();
            balanceUpdate.put("Balance", updatedBalanceStr);

            documentReference.update(balanceUpdate)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            balance.setText(updatedBalanceStr);
                            Toast.makeText(CashierProfilePage.this, "Balance Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CashierProfilePage.this, "Failed to Update Balance: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Firestore", "Error updating balance", e);
                        }
                    });
        } else {
            Toast.makeText(this, "User is not authenticated.", Toast.LENGTH_SHORT).show();
            Log.e("Authentication", "User is not authenticated.");
        }
    }

    private String getPositionText(String userLevel) {
        switch (userLevel) {
            case "user":
                return "User";
            case "kitchen":
                return "Kitchen";
            case "cashier":
                return "Cashier";
            default:
                return "Unknown Position";
        }
    }
}
