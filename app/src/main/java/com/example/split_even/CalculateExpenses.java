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
    static  ArrayList<PurchasedItem> purchasedItemsArr;
    static ArrayList<UserExpenses> userExpensesSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_expenses);
        System.out.println("Test calculation screen");

        user1 = findViewById(R.id.username1Tv);
        user2 = findViewById(R.id.username2Tv);
        user3 = findViewById(R.id.username3Tv);

        user1_expenses = findViewById(R.id.user1ExpensesTv);
        user1_expenses = findViewById(R.id.user2ExpensesTv);
        user1_expenses = findViewById(R.id.user3ExpensesTv);


        // Get all purchased items data from DB

        purchasedItemsArr = new ArrayList<PurchasedItem>();
        userExpensesSum = new ArrayList<UserExpenses>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PurchasedItems");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    purchasedItemsArr.add(child.getValue(PurchasedItem.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference myRef1 = database.getReference("Users");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    String tempEmail = child.getValue(User.class).getEmail();
                    String tempUsername = child.getValue(User.class).getName();
                    UserExpenses ux = new UserExpenses(tempUsername);
                    for (int i = 0; i<purchasedItemsArr.size();i++)
                    {
                        if (purchasedItemsArr.get(i).getUserEmail().equals(tempEmail))
                        {
                            ux.setExpenses(ux.getExpenses() + purchasedItemsArr.get(i).getPrice());
                        }
                    }
                    userExpensesSum.add(ux);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        user1.setText(userExpensesSum.get(0).getUserName());
        user2.setText(userExpensesSum.get(1).getUserName());
        user3.setText(userExpensesSum.get(2).getUserName());

        user1_expenses.setText(Double.toString(userExpensesSum.get(0).getExpenses()));
        user2_expenses.setText(Double.toString(userExpensesSum.get(1).getExpenses()));
        user3_expenses.setText(Double.toString(userExpensesSum.get(2).getExpenses()));




        //Todo : put the info into the textview like -->   user1.setText(""); --> while we get the text from DB



    }
}
