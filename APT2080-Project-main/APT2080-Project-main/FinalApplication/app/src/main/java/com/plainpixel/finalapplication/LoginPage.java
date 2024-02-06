package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button login;
    TextView sign_up, forgotPassword;
    String token;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.sign_up);
        forgotPassword = findViewById(R.id.forgotPassword);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email cannot be empty");
                } else if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password cannot be empty");
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                        DocumentReference df = firebaseStore.collection("Users").document(uid);
                                        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                                                if (Objects.equals(documentSnapshot.getString("level"), "user")){
                                                    Intent intent = new Intent(LoginPage.this, LandingPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else if (Objects.equals(documentSnapshot.getString("level"), "cashier")) {
                                                    Intent intent = new Intent(LoginPage.this, CashierLandingPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else if (Objects.equals(documentSnapshot.getString("level"), "kitchen")) {
                                                    Intent intent = new Intent(LoginPage.this, KitchenLandingPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });
                                        Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        df.update("token", token);
                                    }
                                    else {
                                        Toast.makeText(LoginPage.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}