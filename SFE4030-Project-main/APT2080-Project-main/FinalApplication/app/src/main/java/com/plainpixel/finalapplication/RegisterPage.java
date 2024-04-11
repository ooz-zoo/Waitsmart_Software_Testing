package com.plainpixel.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {

    TextInputEditText editName, editContactNumber, editTextEmail, editTextPassword;
    Button sign_up;
    TextView login;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        editName = findViewById(R.id.name);
        editContactNumber = findViewById(R.id.contact_number);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.sign_up);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, contactNumber, email, password;
                name = String.valueOf(editName.getText());
                contactNumber = String.valueOf(editContactNumber.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                int balance = 0;

                String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                Pattern pattern = Pattern.compile(emailRegex);

                if (TextUtils.isEmpty(name)){
                    editName.setError("Name cannot be empty");
                } else if (TextUtils.isEmpty(contactNumber)){
                    editContactNumber.setError("Contact number cannot be empty");
                } else if (contactNumber.length() != 10){
                    editContactNumber.setError("Invalid length of contact number");
                } else if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email cannot be empty");
                } else if (!pattern.matcher(email).matches()) {
                    editTextEmail.setError("Invalid Email");
                } else if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password cannot be empty");
                } else if (password.length()<8) {
                    editTextPassword.setError("At least 8 Characters required");
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .build();

                                        // Storing data in FireStore
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        DocumentReference df = firebaseStore.collection("Users").document(user.getUid());
                                        Map<String,Object> userinfo = new HashMap<>();
                                        userinfo.put("FullName", editName.getText().toString());
                                        userinfo.put("ContactNumber",editContactNumber.getText().toString());
                                        userinfo.put("ContactEmail",editTextEmail.getText().toString());
                                        userinfo.put("Balance","0");
                                        userinfo.put("level","user");
                                        df.set(userinfo);

                                        Toast.makeText(RegisterPage.this, "Registration Successful" , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(RegisterPage.this, "User already exists!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}