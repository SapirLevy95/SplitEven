package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class add_shared_item extends AppCompatActivity {

    static String tempItemName;
    private FirebaseAuth mAuth;
    static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shared_item);
        mAuth = FirebaseAuth.getInstance();
        addItemToSharedItems("Milk");

    }

    void addItemToSharedItems (String itemName1)
    {
        tempItemName = itemName1;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SharedItems");

        myRef.orderByKey().equalTo(itemName1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Toast.makeText(add_shared_item.this, "The item is already in shared items list",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                    String userID = user.getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("OfferedItems").child(tempItemName).child("userEmail");
                    User tempUser = dataSnapshot.getValue(User.class);
                    userEmail = tempUser.getEmail();
                    myRef.setValue(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
