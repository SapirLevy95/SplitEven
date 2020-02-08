package com.example.split_even;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_page extends AppCompatActivity {

    EditText mloginEmailEd;
    EditText mloginPasswordEd;
    Button mcontinueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        mloginEmailEd = findViewById(R.id.emailEt);
        mloginPasswordEd = findViewById(R.id.passwordEt);
        mcontinueBtn = findViewById(R.id.continueBtn);

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
//        String email= mloginEmailEd.getText().toString() ;
//        String password = mloginPasswordEd.getText().toString();
//
//
//        Intent intent = new Intent(getApplicationContext(),???.class);
//        intent.putExtra("password", password);
//        intent.putExtra("email", email);
//        startActivity(intent);


    }

    private boolean checkValidityLogin() {

        String email = mloginEmailEd.getText().toString();
        String password = mloginPasswordEd.getText().toString();

        if ( email.length()>0 && password.length()>0){
            return true;
        }
        else return false;
    }
}
