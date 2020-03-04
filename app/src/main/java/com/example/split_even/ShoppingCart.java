package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//https://www.javatpoint.com/android-custom-listview
public class ShoppingCart extends AppCompatActivity {
    public ArrayList<String> shoppingCardItems = new ArrayList<String>();
    ArrayAdapter<String> shoppingCartAdapter;
    ListView mListView;
    Button addBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mListView = findViewById(R.id.itemsLv);
        addBt = findViewById(R.id.addBt);
        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shopping_cart_view) {
                createPopup(shopping_cart_view);
            }
        });

        shoppingCartAdapter = new ShoppingCartAdapter(this);
        mListView.setAdapter(shoppingCartAdapter);

        for (int i = 0; i <= 10; i++) {
            shoppingCardItems.add("Sapir Levy " + i);
        }
        shoppingCardItems.add("Sapir ");

        shoppingCartAdapter.notifyDataSetChanged(); // Update the XML with the new data, call getView()
    }

    private void createPopup(View shopping_cart_view) {
        // create the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_item_popup, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(shopping_cart_view, Gravity.CENTER, 0, 0);

        // Add items to spinner
        final ArrayList<String> sharedItemsList = new ArrayList<String>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SharedItems");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    sharedItemsList.add(child.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Spinner spinner = popupWindow.getContentView().findViewById(R.id.select_item_spinner);
        ArrayAdapter<String> popupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sharedItemsList);
        spinner.setAdapter(popupAdapter);

        //Add button
        Button addButton = popupWindow.getContentView().findViewById(R.id.addBt);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shopping_cart_view) {
                Spinner spinner = popupWindow.getContentView().findViewById(R.id.select_item_spinner);
                String selectedItem = (String) spinner.getSelectedItem();
                Log.d("myTag", selectedItem);

                EditText priceEt = popupWindow.getContentView().findViewById(R.id.priceEt);
                String priceText = priceEt.getText().toString();
                Log.d("myTag", priceText);


                //Add the new item to the shopping cart list
                shoppingCardItems.add(selectedItem);
                shoppingCartAdapter.notifyDataSetChanged();
                //TODO Add the new item to DB
                popupWindow.dismiss();
            }
        });

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void add_shoping_cart_item(String item) {
        //need to add if inStock | add the DB | update the TOTAL viewtext **********
        shoppingCardItems.add(item);
        shoppingCartAdapter.notifyDataSetChanged();
    }

    public void remove_shoping_cart_item(String item) {
        // change the inStock to false | delete from DB | update the TOTAL viewtext **********
        shoppingCardItems.remove(item);
        shoppingCartAdapter.notifyDataSetChanged();
    }

}