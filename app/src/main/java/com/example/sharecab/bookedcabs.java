package com.example.sharecab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class bookedcabs extends AppCompatActivity {
    TextView otp,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedcabs);
        otp=findViewById(R.id.otp);
        uid=findViewById(R.id.uid);
        Intent intent =getIntent();
        String s =intent.getStringExtra("Uid").toString();
        Toast.makeText(this, s+" new", Toast.LENGTH_SHORT).show();
        uid.setText("UID: " +s);
        s =intent.getStringExtra("otp").toString();
        otp.setText("OTP: " +s);
    }
}
