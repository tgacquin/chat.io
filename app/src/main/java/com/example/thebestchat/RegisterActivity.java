package com.example.thebestchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    Button register;
    EditText password, email, name;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.enteremail_reg);
        register = findViewById(R.id.signup_reg);
        password = findViewById(R.id.enterpassword_reg);
        name= findViewById(R.id.enter_realname);
        mAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordTxt = password.getText().toString().trim();
                String emailTxt = email.getText().toString().trim();
                String nameTxt = name.getText().toString().trim();

                if (emailTxt.isEmpty()) {
                    email.setError("Email required");
                    email.requestFocus();
                    return;
                }

                if (nameTxt.isEmpty()) {
                    email.setError("Email required");
                    email.requestFocus();
                    return;
                }

                if (passwordTxt.isEmpty()) {
                    email.setError("Password required");
                    email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
                    email.setError("Please provide valid email");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                                    if (isNew == true) {
                                        User user = new User(emailTxt, passwordTxt,nameTxt,mAuth.getUid());
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(mAuth.getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(RegisterActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                                            Intent i = new Intent(RegisterActivity.this, Friends.class);
                                                            startActivity(i);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!1", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Failed to register! Try again!2", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }


}