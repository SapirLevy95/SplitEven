package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class register_page extends AppCompatActivity {

    EditText mregisterFullNameEd;
    EditText mregisterEmailEd;
    EditText mregisterPasswordEd;
    EditText mregisterConfirmPasswordEd;
    Button mRegisterContinueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mregisterFullNameEd = findViewById(R.id.full_nameEt);
        mregisterEmailEd = findViewById(R.id.email_registerEt);
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
        String email = mregisterEmailEd.getText().toString() ;
        String password = mregisterEmailEd.getText().toString() ;


        Intent intent = new Intent(getApplicationContext(),Main_menu.class);
        intent.putExtra("full_name", full_name);
        intent.putExtra("email",email );
        intent.putExtra("password",password );

        startActivity(intent);

    }

    private boolean checkValidityLogin() {
        String full_name = mregisterFullNameEd.getText().toString() ;
        boolean is_valid_name = Pattern.matches("^[ A-Za-z]+$",full_name );

        String email = mregisterEmailEd.getText().toString() ;
        boolean is_valid_email = Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", email);

        String password = mregisterPasswordEd.getText().toString() ;
        boolean is_valid_password = Pattern.matches("^[ A-Za-z0-9]+$", password);

        String confirm_password = mregisterConfirmPasswordEd.getText().toString() ;

        return is_valid_name && is_valid_email && is_valid_password && password.equals(confirm_password);

    }
}
