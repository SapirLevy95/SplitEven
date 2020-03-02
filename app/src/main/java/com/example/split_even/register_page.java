package com.example.split_even;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register_page extends AppCompatActivity {

    private Button mcontinueBtn;
    private EditText mregisterFullnameEd;
    private EditText mregisterEmailEd;
    private EditText mregisterPasswordEd;
    private EditText mregisterPasswordConfEd;
    private FirebaseAuth mAuth;
    private boolean fieldsRestrictionsOK = false;
 //   private static boolean adminExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        mAuth = FirebaseAuth.getInstance();

        mcontinueBtn = findViewById(R.id.continue_registerBtn);
        mregisterFullnameEd = findViewById(R.id.full_nameEt);
        mregisterEmailEd = findViewById(R.id.email_registerEt);
        mregisterPasswordEd = findViewById(R.id.password_registerEt);
        mregisterPasswordConfEd = findViewById(R.id.confirm_password_registerEt);

        mcontinueBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                while (fieldsRestrictionsOK == false)
                {
                    if (verifyFieldsRestrictions() == true)
                    {
                        fieldsRestrictionsOK = true;
                    }
                }

                mAuth.createUserWithEmailAndPassword(mregisterEmailEd.getText().toString(), mregisterPasswordEd.getText().toString())
                        .addOnCompleteListener(register_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                    String userID = user.getUid();
                                    User currUser = new User (userID, mregisterEmailEd.getText().toString(), mregisterFullnameEd.getText().toString(),false);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Users").child(userID);
                                    myRef.setValue(currUser);
                                    Toast.makeText(register_page.this, "Registration successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Main_menu.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(register_page.this, "Registration failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

     public boolean verifyFieldsRestrictions ()
    {
        if (mregisterPasswordEd.getText().toString().length() < 6 || mregisterPasswordEd.getText().toString().length() > 16)
        {
            Toast.makeText(register_page.this, "Password length should be between 6 to 16",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (!mregisterPasswordEd.getText().toString().equals(mregisterPasswordConfEd.getText().toString()))
        {
            Toast.makeText(register_page.this, "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
