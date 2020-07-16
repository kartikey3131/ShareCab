package com.example.sharecab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class Profile extends AppCompatActivity {

    private TextView rider_name;
    private TextView rider_contact;
    private TextView rider_model;
    private TextView rider_number;
    private TextView rider_price;
    private TextView rider_status;
    String currentuser;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(Profile.this);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            currentuser =FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        else{
            currentuser =googleSignInAccount.getId();
        }
        rider_name =findViewById(R.id.rider_name);
        rider_contact =findViewById(R.id.rider_contact);
        rider_model =findViewById(R.id.rider_model);
        rider_number =findViewById(R.id.rider_number);
        rider_price =findViewById(R.id.rider_price);
        rider_status =findViewById(R.id.rider_status);
        rider_status.setVisibility(View.INVISIBLE);
        rider_model.setVisibility(View.INVISIBLE);
        rider_number.setVisibility(View.INVISIBLE);
        rider_price.setVisibility(View.INVISIBLE);
        Intent intent =getIntent();
        final String p =intent.getStringExtra("Type").toString();
        rider_name.setText(p);
        if(p.equals("Drivers")){
            rider_status.setVisibility(View.VISIBLE);
            rider_model.setVisibility(View.VISIBLE);
            rider_number.setVisibility(View.VISIBLE);
            rider_price.setVisibility(View.VISIBLE);
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(p);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(p.equals("Drivers")){
                    if (snapshot.child("uid -1").exists()) {
                        User2 u = snapshot.child(currentuser).getValue(User2.class);
                        rider_name.setText(u.getName());
                        rider_contact.setText(u.getContact());
                        rider_price.setText(u.getPrice());
                        rider_number.setText(u.getCarNumber());
                        rider_model.setText(u.getModel());
                        rider_status.setText(u.getAvailability());
                    }
                    else{
                        Toast.makeText(Profile.this, currentuser, Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    if(snapshot.child(currentuser).exists()) {
                        User1 u = snapshot.child(currentuser).getValue(User1.class);
                        rider_name.setText(u.getName());
                        rider_contact.setText(u.getContact());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
