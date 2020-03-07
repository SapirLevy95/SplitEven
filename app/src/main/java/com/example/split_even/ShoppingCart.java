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

//https://www.javatpoint.com/android-custom-listview
public class ShoppingCart extends AppCompatActivity {

    public ArrayList<String> shoppingCartItems = new ArrayList<String>();
    public ArrayList<String> sharedItemsList = new ArrayList<>();
    public ArrayList<PurchasedItem> listItems = new ArrayList<PurchasedItem>();
    private ArrayAdapter<String> shoppingCartAdapter;
    private ListView mListView;
    private Button addBtn;
    private FirebaseAuth mAuth;
    static String userEmail;
    private Button checkoutButton;
    private Button addButton_popup;

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


        shoppingCartAdapter.notifyDataSetChanged(); // Update the XML with the new data, call getView()

        // Checkout - Save items in shopping cart to database

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shopping_cart_view) {
                System.out.println("Checkout performed");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("PurchasedItems");

                for (int i=0;i<listItems.size();i++)
                {
                    myRef.push().setValue(listItems.get(i));
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
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    sharedItemsList.add(child.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for (int i=0;i<sharedItemsList.size();i++)
        {
            System.out.println(sharedItemsList.get(i));
        }
        List<String> temp1 = new ArrayList<String>();
        //TODO: add to list insead of the item above
        temp1.add("Eggs");
        temp1.add("Milk");

        Spinner spinner = popupWindow.getContentView().findViewById(R.id.select_item_spinner);
        ArrayAdapter<String> popupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temp1);
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
                String selectedItem = (String) spinner.getSelectedItem();

                EditText priceEt = popupWindow.getContentView().findViewById(R.id.priceEt);
                double itemPrice = Double.parseDouble(priceEt.getText().toString());

                System.out.println("Before reading user details from DB");
                DatabaseReference myRef = database.getReference("Users").child(userID);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        userEmail = user.getEmail();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                PurchasedItem temp = new PurchasedItem(selectedItem, itemPrice, userEmail);
                System.out.println ("Item name: " + temp.getItemName() + " and item price: " + temp.getPrice() + " and user mail: " + temp.getUserEmail());
                listItems.add(temp);

                //Add the new item to the shopping cart list
                shoppingCartItems.add(selectedItem);
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
        shoppingCartItems.add(item);
        shoppingCartAdapter.notifyDataSetChanged();
    }

    public void remove_shoping_cart_item(String item) {
        // change the inStock to false | delete from DB | update the TOTAL viewtext **********
        shoppingCartItems.remove(item);
        shoppingCartAdapter.notifyDataSetChanged();
    }

}