package com.recipeapp.recipeapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.recipeapp.recipeapp.R;
import com.recipeapp.recipeapp.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailAddressLogin);
        password = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.buttonLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        //verificam daca utilizatorul este deja conectat
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString().trim();
                String strPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(strEmail)){
                    email.setError("Insert an email!");
                }

                if(TextUtils.isEmpty(strPassword)){
                    password.setError("Insert a password!");
                }

                if (strPassword.length() < 8){
                    password.setError("The password must have at least 8 characters!");
                }


                firebaseAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Something went wrong." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    public void goRegister(View view){
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

}