package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_page extends AppCompatActivity {

    EditText mloginUsernameEd;
    EditText mloginPasswordEd;
    Button mcontinueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        mloginUsernameEd = findViewById(R.id.username_loginEt);
        mloginPasswordEd = findViewById(R.id.password_loginEt);
        mcontinueBtn = findViewById(R.id.continue_loginBtn);

//        if (...)
//        mcontinueBtn.setVisibility(View.INVISIBLE);
//
//        mcontinueBtn.setText();


        mcontinueBtn.setOnClickListener(new View.OnClickListener() {
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
        String username= mloginUsernameEd.getText().toString() ;
        String password = mloginPasswordEd.getText().toString();


        Intent intent = new Intent(getApplicationContext(),Main_menu.class);
        intent.putExtra("password", password);
        intent.putExtra("username",username );
        startActivity(intent);


    }

    private boolean checkValidityLogin() {

        String username = mloginUsernameEd.getText().toString();
        String password = mloginPasswordEd.getText().toString();

        if ( username.length()>0 && password.length()>0){
            return true;
        }
        else return false;
    }
}
