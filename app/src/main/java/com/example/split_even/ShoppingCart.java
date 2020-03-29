package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//https://www.javatpoint.com/android-custom-listview
public class ShoppingCart extends AppCompatActivity {

    public ArrayList<String> shopping_cart_items_display = new ArrayList<String>();
    public List<String> shared_items = new ArrayList<String>();
    public ArrayList<PurchasedItem> purchased_items_to_db = new ArrayList<PurchasedItem>();
    private ArrayAdapter<String> shoppingCartAdapter;
    private ListView mListView;
    private Button addBtn;
    private FirebaseAuth mAuth;
    public static String userEmail;
    private Button checkoutButton;
    private Button addButton_popup;
    static  String selectedItem;
    static double itemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        mAuth = FirebaseAuth.getInstance();
        mListView = (ListView)findViewById(R.id.itemsLv);
        addBtn = (Button)findViewById(R.id.addBtn);
        checkoutButton = (Button)findViewById(R.id.checkoutBt);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shopping_cart_view) {
                createPopup(shopping_cart_view);
            }
        });

        shoppingCartAdapter = new ShoppingCartAdapter(this);
        mListView.setAdapter(shoppingCartAdapter);


        shoppingCartAdapter.notifyDataSetChanged();

        // Checkout - Save items in shopping cart to database

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shopping_cart_view) {
                System.out.println("Checkout performed");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("PurchasedItems");

                for (int i = 0; i< purchased_items_to_db.size(); i++)
                {
                    myRef.push().setValue(purchased_items_to_db.get(i));
                }

                Intent intent = new Intent(getApplicationContext(),Main_menu.class);
                startActivity(intent);

            }
        });

        // -----------------------------------------------------------------------

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

        // Add shared items to spinner

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SharedItems");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    shared_items.add(child1.child("itemName").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        List<String> temp_shared_items = new ArrayList<String>();
        temp_shared_items.add("Eggs");
        temp_shared_items.add("Milk");
        temp_shared_items.add("Cheese");
        temp_shared_items.add("Soap");

        Spinner spinner = popupWindow.getContentView().findViewById(R.id.select_item_spinner);
        ArrayAdapter<String> popupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temp_shared_items);
        spinner.setAdapter(popupAdapter);

        // Add item to shopping cart

        addButton_popup = popupWindow.getContentView().findViewById(R.id.addBtn_popup);
        addButton_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shopping_cart_view) {
                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                String userID = user.getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                Spinner spinner = popupWindow.getContentView().findViewById(R.id.select_item_spinner);
                selectedItem = (String) spinner.getSelectedItem();

                EditText priceEt = popupWindow.getContentView().findViewById(R.id.priceEt);
                itemPrice = Double.parseDouble(priceEt.getText().toString());

                DatabaseReference myRef = database.getReference("Users").child(userID);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        userEmail = user.getEmail();
                        PurchasedItem temp = new PurchasedItem(selectedItem, itemPrice, userEmail);
                        purchased_items_to_db.add(temp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // Add the new item to the shopping cart list
                shopping_cart_items_display.add(selectedItem);
                shoppingCartAdapter.notifyDataSetChanged();
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

    public void add_shopping_cart_item(String item) {
        //need to add if inStock | add the DB | update the TOTAL viewtext **********
        shopping_cart_items_display.add(item);
        shoppingCartAdapter.notifyDataSetChanged();
    }

    public void remove_shopping_cart_item(String item) {
        // change the inStock to false | delete from DB | update the TOTAL viewtext **********
        shopping_cart_items_display.remove(item);
        shoppingCartAdapter.notifyDataSetChanged();
    }

}