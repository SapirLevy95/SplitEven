package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_menu extends AppCompatActivity {

    Button mManageShoppingCartBtn;
    Button mLogOffBtn;
    Button mExitAppBtn;
    Button approveNewItemBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mManageShoppingCartBtn = findViewById(R.id.go_to_manage_cart_pageBt);
        mLogOffBtn = findViewById(R.id.log_of_mainBt);
        mExitAppBtn = findViewById(R.id.exit_app_mainBt);
        approveNewItemBt = findViewById(R.id.approve_new_itemBt);


        mManageShoppingCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToManageShoppingCart();
            }
        });

        approveNewItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToApproveNewItemBt();
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

    private void moveToApproveNewItemBt() {
        Intent intent = new Intent(getApplicationContext(), approveNewItem.class);
        startActivity(intent);
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

