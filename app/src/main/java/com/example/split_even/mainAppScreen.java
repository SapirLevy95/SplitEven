package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class mainAppScreen extends AppCompatActivity {

    TextView mdMainapptv1;
    TextView mdMainapptv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_screen);

        mdMainapptv1= findViewById(R.id.emailEt);
        mdMainapptv2 = findViewById(R.id.passwordEt);

    }
}
