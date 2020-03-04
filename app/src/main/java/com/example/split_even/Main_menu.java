package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_menu extends AppCompatActivity {

    Button mManageShoppingCartBtn;
    Button mLogOffBtn;
    Button mExitAppBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mManageShoppingCartBtn = findViewById(R.id.go_to_manage_cart_pageBt);
        mLogOffBtn = findViewById(R.id.log_of_mainBt);
        mExitAppBtn = findViewById(R.id.exit_app_mainBt);


        mManageShoppingCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToManageShoppingCart();
            }
        });

        mLogOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToOpeningScreen();
            }
        });

        mExitAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });



}

    private void moveToOpeningScreen() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    private void moveToManageShoppingCart() {
        Intent intent = new Intent(getApplicationContext(),ShoppingCart.class);
        startActivity(intent);
    }
    }

