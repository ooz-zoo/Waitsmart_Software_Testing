package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button resetButton;
    TextView backButton;
    String strEmail;
    EditText editEmail;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backButton = findViewById(R.id.go_back);
        resetButton = findViewById(R.id.resetButton);
        editEmail = findViewById(R.id.email);

        firebaseAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = editEmail.getText().toString().trim();
                if (TextUtils.isEmpty(strEmail)){
                    editEmail.setError("Email field cannot be empty");
                } else {
                    ResetPassword();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, LoginPage.class);
                startActivity(intent);
            }
        });



    }

    private void ResetPassword(){
        firebaseAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this, "Reset password link has been sent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPassword.this, LoginPage.class);
                startActivity(intent);
            }
        }). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}