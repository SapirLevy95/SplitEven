package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main_menu extends AppCompatActivity {

    Button mManageShoppingCartBtn;
    Button mLogOffBtn;
    Button mExitAppBtn;
    Button approveNewItemBt;
    Button addOfferItemBt;
    EditText offerNewItemEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mManageShoppingCartBtn = findViewById(R.id.go_to_manage_cart_pageBt);
        mLogOffBtn = findViewById(R.id.log_of_mainBt);
        mExitAppBtn = findViewById(R.id.exit_app_mainBt);
        approveNewItemBt = findViewById(R.id.approve_new_itemBt);
        addOfferItemBt = findViewById(R.id.offerItemB);
        offerNewItemEt = findViewById(R.id.add_iteEt);

        if (!AppState.user_name.equals("admin@gmail.com")) {
            System.out.println("user_name");
            System.out.println(AppState.user_name);
            approveNewItemBt.setVisibility(View.INVISIBLE);
        } else{
            approveNewItemBt.setVisibility(View.VISIBLE);
        }

        mManageShoppingCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToManageShoppingCart();
            }
        });

        addOfferItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String offered_item;
                offered_item = offerNewItemEt.getText().toString();
                if (!offered_item.equals("")) {
                    //Todo : put the item into the DB if not exists in DB
                    offerNewItemEt.setText("");
                }
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
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void moveToManageShoppingCart() {
        Intent intent = new Intent(getApplicationContext(), ShoppingCart.class);
        startActivity(intent);
    }
}

