package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class add_to_shopping_cart extends AppCompatActivity {

    public ArrayList<PurchasedItem> listItems = new ArrayList<PurchasedItem>();
    private FirebaseAuth mAuth;
    static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

          }

          public void addItemToShoppingCart(String itemName, double itemPrice)
          {
              FirebaseUser user = mAuth.getInstance().getCurrentUser();
              String userID = user.getUid();
              FirebaseDatabase database = FirebaseDatabase.getInstance();

              // check if item in stock

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

              PurchasedItem temp = new PurchasedItem(itemName, itemPrice, userEmail);
              listItems.add(temp);
          }

          public void addShoppingCartToDB()
          {
              FirebaseDatabase database = FirebaseDatabase.getInstance();
              DatabaseReference myRef = database.getReference("PurchasedItems");

              for (int i=0;i<listItems.size();i++)
              {
                  myRef.push().setValue(listItems.get(i));
              }
          }
}
