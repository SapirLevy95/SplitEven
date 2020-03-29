package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Main_menu extends AppCompatActivity {

    Button mManageShoppingCartBtn;
    Button mLogOffBtn;
    Button mExitAppBtn;
    Button approveNewItemBt;
    Button addOfferItemBt;
    EditText offerNewItemEt;
    static String offeredItem;
    Button calculationScreenBt;


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
        calculationScreenBt = findViewById(R.id.calculationExpansesScreenBt);

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
                offeredItem =  offerNewItemEt.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("OfferedItems");
                myRef.orderByChild("itemName").equalTo(offeredItem).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            Toast.makeText(Main_menu.this, "Item already exists",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            offerNewItemEt.setText("");
                            Map<String, String> temp = new HashMap<String, String>();
                            temp.put("itemName", offeredItem);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef1 = database.getReference("OfferedItems");
                            myRef1.push().setValue(temp);
                            Toast.makeText(Main_menu.this, "Item was added to Offered Items",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        approveNewItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToApproveNewItemBt();
            }
        });


        calculationScreenBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Test press button calculation screen");
                moveToCalculationScreen();
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

    private void moveToCalculationScreen() {
        Intent intent = new Intent(getApplicationContext(), CalculateExpenses.class);
        startActivity(intent);
        System.out.println("Test move to calculation screen");
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

