package com.example.sharecab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
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
    private Switch status;
    String search;
    String currentuser;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
        status=findViewById(R.id.status);
        rider_status.setVisibility(View.INVISIBLE);
        rider_model.setVisibility(View.INVISIBLE);
        rider_number.setVisibility(View.INVISIBLE);
        rider_price.setVisibility(View.INVISIBLE);
        Intent intent =getIntent();
        final String p =intent.getStringExtra("Type").toString();
        rider_name.setText(p);
       // Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
        if(p.equals("Drivers")){
            rider_status.setVisibility(View.VISIBLE);
            rider_model.setVisibility(View.VISIBLE);
            rider_number.setVisibility(View.VISIBLE);
            rider_price.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Drivers");
                    if (isChecked) {
                        //Toast.makeText(Profile.this, mAuth.getUid()+" :"+"Un Available", Toast.LENGTH_SHORT).show();
                        ref.child(mAuth.getUid()).child("availability").setValue("Available");

                    } else {
                        ref.child(mAuth.getUid()).child("availability").setValue("Un Available");
                        //Toast.makeText(Profile.this, mAuth.getUid()+" :"+"Available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        //Toast.makeText(this, mAuth.getUid().toString(), Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Drivers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(p.equals("Drivers")){
                    if (snapshot.child(mAuth.getUid()).exists()) {
                        //Toast.makeText(Profile.this, "70", Toast.LENGTH_SHORT).show();

                        User2 u = snapshot.child(mAuth.getUid()).getValue(User2.class);
                        rider_name.setText(u.getName());
                        rider_contact.setText(u.getContact());
                        rider_price.setText(u.getPrice());
                        rider_number.setText(u.getCarNumber());
                        rider_model.setText(u.getModel());
                        rider_status.setText(u.getAvailability());

                        if(u.getAvailability().trim().equals("Available")){
                           // Toast.makeText(Profile.this, "100", Toast.LENGTH_SHORT).show();
                            status.setChecked(true);
                        }
                        else{
                            //Toast.makeText(Profile.this, "104", Toast.LENGTH_SHORT).show();
                            status.setChecked(false);
                        }

                   }
                    else{
                        Toast.makeText(Profile.this, currentuser, Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    if(snapshot.child(currentuser).exists()) {

                        Toast.makeText(Profile.this, "85", Toast.LENGTH_SHORT).show();
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
