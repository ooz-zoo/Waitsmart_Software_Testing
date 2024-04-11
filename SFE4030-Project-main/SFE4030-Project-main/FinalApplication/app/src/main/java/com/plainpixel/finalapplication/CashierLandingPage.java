package com.plainpixel.finalapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CashierLandingPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogout;

    private SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_landing_page);

        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.logout);

        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false); // Disable focus on start

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(CashierLandingPage.this, userArrayList);

        recyclerView.setAdapter(myAdapter);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enable focus and show the keyboard when the SearchView is clicked
                searchView.setFocusableInTouchMode(true);
                searchView.requestFocus();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Search the data in Firestore when the user submits the query
                searchFirebaseData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Search the data in Firestore as the user types
                searchFirebaseData(newText);
                return false;
            }
        });

        EventChangeListener();
    }

    private void searchFirebaseData(String newText) {
        String lowercaseQuery = newText.toLowerCase();

        // Clear the current userArrayList to avoid duplicate entries
        userArrayList.clear();

        // Create a Firestore query to fetch all users
        db.collection("Users")
                .orderBy("FullName") // Remove the direction, as it's not necessary for searching
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        try {
                            // Retrieve the user's FullName from the document and convert it to lowercase
                            String fullName = document.getString("FullName");
                            if (fullName != null && fullName.toLowerCase().contains(lowercaseQuery)) {
                                userArrayList.add(document.toObject(User.class));
                            }
                        } catch (Exception e) {
                            Log.e("Firestore Search", "Error parsing Firestore document", e);
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred during the search
                    Log.e("Firestore Search", "Error searching for users: " + e.getMessage());
                });
    }

    private void EventChangeListener() {
        db.collection("Users").orderBy("FullName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firebase error", "Error fetching Firestore data", error);
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                try {
                                    userArrayList.add(dc.getDocument().toObject(User.class));
                                } catch (Exception e) {
                                    Log.e("Firestore error", "Error converting document to User", e);
                                }
                            }
                        }

                        myAdapter.notifyDataSetChanged();
                    }
                });
    }
}
