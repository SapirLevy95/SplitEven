package com.example.split_even;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class approveNewItem extends AppCompatActivity {


    public ArrayList<String> approveItems = new ArrayList<String>();
    public ArrayList<String> sharedItemsList = new ArrayList<>();
    public ArrayList<PurchasedItem> listItems = new ArrayList<PurchasedItem>();
    private ArrayAdapter<String> approve_items_adapeter;
    private ListView mListView;
    private Button back_button;
    private FirebaseAuth mAuth;
    static String userEmail;
    private Button checkoutButton;
    private Button addButton_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_new_item);

        mAuth = FirebaseAuth.getInstance();
        mListView = (ListView) findViewById(R.id.items_to_approveLv);
        back_button = (Button) findViewById(R.id.back_to_main_menuBt);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shopping_cart_view) {
                Intent intent = new Intent(getApplicationContext(), Main_menu.class);
                startActivity(intent);
            }
        });
        approve_items_adapeter = new ApproveItemsAdapter(this);
        mListView.setAdapter(approve_items_adapeter);

        // Load items from OfferedItems

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("OfferedItems");
        myRef.orderByChild("itemName").addListenerForSingleValueEvent(new ValueEventListener() {
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

        // ------------------------------------------------------------------

        for (int i = 0; i <= 10; i++) {
            approveItems.add("Item to approve  " + i);
        }

        approve_items_adapeter.notifyDataSetChanged();
    }


    public void remove_item_form_screen(String item) {
        approveItems.remove(item);
        approve_items_adapeter.notifyDataSetChanged();
    }


}
