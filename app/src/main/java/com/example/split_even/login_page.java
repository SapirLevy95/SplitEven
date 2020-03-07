package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_page extends AppCompatActivity {

    private EditText mloginEmailEd;
    private EditText mloginPasswordEd;
    private Button mcontinueBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();

        mloginEmailEd = findViewById(R.id.email_loginEt);
        mloginPasswordEd = findViewById(R.id.password_loginEt);
        mcontinueBtn = findViewById(R.id.continue_loginBtn);

        mcontinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(mloginEmailEd.getText().toString(), mloginPasswordEd.getText().toString())
                        .addOnCompleteListener(login_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    AppState.user_name = user.getEmail();
                                    String userID = user.getUid();
                                    // if (userID == adminUserID (in DB)) then show admin main menu. else show regular main menu
                                    Toast.makeText(login_page.this, "Login successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Main_menu.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(login_page.this, "Login failed, please try again",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
