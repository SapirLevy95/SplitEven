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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class register_page extends AppCompatActivity {

    private Button mcontinueBtn;
    private EditText mregisterFullnameEd;
    private EditText mregisterEmailEd;
    private EditText mregisterPasswordEd;
    private EditText mregisterPasswordConfEd;
    private FirebaseAuth mAuth;
    static boolean isAdmin = false;

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
              checkValidityLogin();
                mAuth.createUserWithEmailAndPassword(mregisterEmailEd.getText().toString(), mregisterPasswordEd.getText().toString())
                        .addOnCompleteListener(register_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                    String userID = user.getUid();
                                    User currUser = new User (userID, mregisterEmailEd.getText().toString(), mregisterFullnameEd.getText().toString());
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Users");
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (!dataSnapshot.exists())
                                            {
                                                isAdmin = true;
                                                System.out.println("is admin true");
                                            }
                                            else
                                            {
                                                isAdmin = false;
                                                System.out.println("is admin false");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    DatabaseReference myRef1 = database.getReference("Users").child(userID);
                                    myRef1.setValue(currUser);
                                    Toast.makeText(register_page.this, "Registration successful",
                                            Toast.LENGTH_SHORT).show();

                                    if (isAdmin)
                                    {
                                        Intent intent = new Intent(getApplicationContext(),admin_main_menu.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(getApplicationContext(),Main_menu.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(register_page.this, "Registration failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void checkValidityLogin() {
        String full_name = mregisterFullnameEd.getText().toString();
        boolean is_valid_name = Pattern.matches("^[ A-Za-z]+$", full_name);
        String email = mregisterEmailEd.getText().toString();
        boolean is_valid_email = Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", email);
        String password = mregisterPasswordEd.getText().toString();
        boolean is_valid_password = Pattern.matches("^[ A-Za-z0-9]+$", password);
        String confirm_password = mregisterPasswordConfEd.getText().toString();

        if (!is_valid_name)
        {
            Toast.makeText(register_page.this, "Full name should contain letters only (A-Z and/or a-z)",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!is_valid_email)
        {
            Toast.makeText(register_page.this, "Email address is invalid",
                    Toast.LENGTH_SHORT).show();
        }
        else if (!is_valid_password)
        {
            Toast.makeText(register_page.this, "Password should contain letters (A-Z, a-z) and/or digits only (0-9)",
                    Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirm_password))
        {
            Toast.makeText(register_page.this, "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
