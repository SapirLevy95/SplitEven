package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

//https://www.javatpoint.com/android-custom-listview
public class ShoppingCart extends AppCompatActivity {
    public ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
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
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_item_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
        adapter = new ShoppingCartAdapter(this);
        mListView.setAdapter(adapter);

        for (int i = 0; i <= 10; i++) {
            listItems.add("Sapir Levy " + i);
        }
        listItems.add("Sapir ");

        adapter.notifyDataSetChanged(); // Update the XML with the new data, call getView()
    }

    public void add_shoping_cart_item(String item) {
        //need to add if inStock | add the DB | update the TOTAL viewtext **********
        listItems.add(item);
        adapter.notifyDataSetChanged();
    }

    public void remove_shoping_cart_item(String item) {
        // change the inStock to false | delete from DB | update the TOTAL viewtext **********
        listItems.remove(item);
        adapter.notifyDataSetChanged();
    }

}
