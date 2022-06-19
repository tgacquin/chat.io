package com.example.thebestchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView email;
    TextView password;
    Button signIn;
    Button register;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.enteremail);
        password=findViewById(R.id.enterpassword);
        signIn=findViewById(R.id.signin);
        register=findViewById(R.id.register);
        mAuth=FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt=email.getText().toString().trim();
                String passwordTxt = password.getText().toString().trim();
                if (emailTxt.isEmpty()) {
                    email.setError("Username required");
                    email.requestFocus();
                    return;
                }
                if (passwordTxt.isEmpty()) {
                    password.setError("Username required");
                    password.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Success", Toast.LENGTH_LONG);
                            startActivity(new Intent(LoginActivity.this, Friends.class));
                        } else {
                            Toast.makeText(LoginActivity.this,"Failed to login, check credentials.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


}