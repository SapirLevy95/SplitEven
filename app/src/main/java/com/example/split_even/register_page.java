package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register_page extends AppCompatActivity {

    EditText mregisterFullNameEd;
    EditText mregisterUsernameEd;
    EditText mregisterPasswordEd;
    EditText mregisterConfirmPasswordEd;
    Button mRegisterContinueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mregisterFullNameEd = findViewById(R.id.full_nameEt);
        mregisterUsernameEd= findViewById(R.id.username_registerEt);
        mregisterPasswordEd = findViewById(R.id.password_registerEt);
        mregisterConfirmPasswordEd = findViewById(R.id.confirm_password_registerEt);
        mRegisterContinueBtn= findViewById(R.id.continue_registerBtn);

        mRegisterContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidityLogin()){
                    moveToNextScreen();
                }
                else {
                    Toast.makeText(getBaseContext(),"data is not valid",Toast.LENGTH_LONG).show();}
            }
        });



    }

    private void moveToNextScreen() {
        String full_name = mregisterFullNameEd.getText().toString() ;
        String username = mregisterUsernameEd.getText().toString() ;
        String password = mregisterUsernameEd.getText().toString() ;


        Intent intent = new Intent(getApplicationContext(),Main_menu.class);
        intent.putExtra("full_name", full_name);
        intent.putExtra("username",username );
        intent.putExtra("password",password );

        startActivity(intent);

    }

    private boolean checkValidityLogin() {

        String full_name = mregisterFullNameEd.getText().toString() ;
        boolean is_full_name_in_pattern = Pattern.matches("^[ A-Za-z]+$",full_name );

        String username = mregisterUsernameEd.getText().toString() ;
        boolean is_username_in_pattern = Pattern.matches("^[ A-Za-z0-9]+$", username);

        String password = mregisterPasswordEd.getText().toString() ;
        boolean is_password_in_pattern = Pattern.matches("^[ A-Za-z0-9]+$", password);

        String confirm_password = mregisterConfirmPasswordEd.getText().toString() ;

        if (is_full_name_in_pattern && is_username_in_pattern &&  is_password_in_pattern &&  password.equals(confirm_password))
        {
            return true;
        }
        return false;

    }
}
