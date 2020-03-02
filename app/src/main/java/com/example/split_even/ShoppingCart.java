package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

//https://www.javatpoint.com/android-custom-listview
public class ShoppingCart extends AppCompatActivity {


    public ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mListView = findViewById(R.id.itemsLv);
//        adapter = new ArrayAdapter<S
//        tring>(this,android.R.layout.simple_list_item_1, listItems);
        adapter = new ShoppingCartAdapter(this);
        mListView.setAdapter(adapter);

        for (int i = 0; i <= 10; i++) {
            listItems.add("Sapir Levy "+ i);
        }
        listItems.add("Sapir ");

        adapter.notifyDataSetChanged(); // Update the XML with the new data, call getView()
    }

    public void addItemToShoppingCart(PurchasedItem item) {

    }

    public void insertShoppingCartToDB(PurchasedItem item) {
        //need to add if inStock | add the DB | update the TOTAL viewtext **********
       // listItems.add(item);
        adapter.notifyDataSetChanged();
    }

    public void remove_shoping_cart_item(String item) {
        // change the inStock to false | delete from DB | update the TOTAL viewtext **********
        listItems.remove(item);
        adapter.notifyDataSetChanged();
    }

}
