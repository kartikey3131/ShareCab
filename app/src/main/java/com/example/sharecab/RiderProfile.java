package com.example.sharecab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RiderProfile extends AppCompatActivity {

    private TextView rider_name;
    private TextView rider_contact;
    private TextView rider_model;
    private TextView rider_number;
    private TextView rider_price;
    private Button book;
    int random_otp;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_profile);
        rider_name =findViewById(R.id.rider_name);
        rider_contact =findViewById(R.id.rider_contact);
        rider_model =findViewById(R.id.rider_model);
        rider_number =findViewById(R.id.rider_number);
        rider_price =findViewById(R.id.rider_price);
        book = findViewById(R.id.book);
        Intent intent =getIntent();
        String s =intent.getStringExtra("Name").toString();
        rider_name.setText("Name : " +s);
        s=intent.getStringExtra("Contact").toString();
        rider_contact.setText("Contact : "+s);
        s=intent.getStringExtra("Model").toString();
        rider_model.setText("Model : "+s);
        s=intent.getStringExtra("Number").toString();
        rider_number.setText("Car Number : "+s);
        s=intent.getStringExtra("Price").toString();
        rider_price.setText("Price : "+s);
        s=intent.getStringExtra("Status").toString();
        final String finalS = s;
        Uid=intent.getStringExtra("Uid").toString();
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(finalS.equals("Unavailable")){
                    Toast.makeText(RiderProfile.this, "Taxi Is unavailable", Toast.LENGTH_SHORT).show();
                }
                else{
                    Random random= new Random();
                    random_otp=random.nextInt(90000)+10000;
                    Toast.makeText(RiderProfile.this,Uid, Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(RiderProfile.this,bookedcabs.class);
                    i.putExtra("otp",String.valueOf(random_otp));
                    i.putExtra("Uid",Uid);
                    startActivity(i);
                }
            }
        });
    }
}
