package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CalculateExpenses extends AppCompatActivity {

    private TextView user1;
    private TextView user2;
    private  TextView user3;
    private TextView user4;
    private TextView user1_expenses;
    private  TextView user2_expenses;
    private TextView user3_expenses;
    private  TextView user4_expenses;
    private TextView totalExpenses;
    static  ArrayList<PurchasedItem> purchased_items;
    static ArrayList<UserExpenses> user_expenses_sum;
    static DatabaseReference get_users_from_db;
    static FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_expenses);

        user1 = findViewById(R.id.username1Tv);
        user2 = findViewById(R.id.username2Tv);
        user3 = findViewById(R.id.username3Tv);

        user1_expenses = findViewById(R.id.user1ExpensesTv);
        user1_expenses = findViewById(R.id.user2ExpensesTv);
        user1_expenses = findViewById(R.id.user3ExpensesTv);


        // Get all purchased items data from DB and save them in purchased_items

        purchased_items = new ArrayList<PurchasedItem>();
        user_expenses_sum = new ArrayList<UserExpenses>();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference get_purchased_items_from_db = database.getReference("PurchasedItems");
        get_purchased_items_from_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    PurchasedItem temp = child.getValue(PurchasedItem.class);
                    purchased_items.add(temp);
                }

                get_users_from_db = database.getReference("Users");
                get_users_from_db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren())
                        {
                            String tempEmail = child.getValue(User.class).getEmail();
                            String tempUsername = child.getValue(User.class).getName();
                            UserExpenses ux = new UserExpenses(tempUsername);
                            for (int i = 0; i<purchased_items.size();i++)
                            {
                                if (purchased_items.get(i).getUserEmail().equals(tempEmail))
                                {
                                    ux.setExpenses(ux.getExpenses() + purchased_items.get(i).getPrice());
                                }

                            }
                            user_expenses_sum.add(ux);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Iterate over registered users and calculate sum of their expenses




        user1.setText(user_expenses_sum.get(0).getUserName());
        user2.setText(user_expenses_sum.get(1).getUserName());
      //  user3.setText(user_expenses_sum.get(2).getUserName());

        user1_expenses.setText(Double.toString(user_expenses_sum.get(0).getExpenses()));
        user2_expenses.setText(Double.toString(user_expenses_sum.get(1).getExpenses()));
      //  user3_expenses.setText(Double.toString(user_expenses_sum.get(2).getExpenses()));

    }
}
