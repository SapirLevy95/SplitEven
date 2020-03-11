package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CalculateExpenses extends AppCompatActivity {

    private TextView user1;
    private TextView user2;
    private  TextView user3;
    private TextView user4;
    private TextView user1_expenses;
    private  TextView user2_expenses;
    private TextView user3_expenses;
    private  TextView user4_expenses;

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

        //Todo : put the info into the textview like -->   user1.setText(""); --> while we get the text from DB












    }
}
