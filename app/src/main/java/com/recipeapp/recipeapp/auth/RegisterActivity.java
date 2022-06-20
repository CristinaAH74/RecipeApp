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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recipeapp.recipeapp.R;
import com.recipeapp.recipeapp.home.HomeActivity;
import com.recipeapp.recipeapp.profile.RegisteredUserInfo;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password, name;
    Button register;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RegisteredUserInfo userInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.emailAddressRegister);
        password = findViewById(R.id.passwordRegister);
        register = findViewById(R.id.buttonRegister);
        name = findViewById(R.id.nameRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://recipeapp-95d17-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference("RegisteredUserInfo");

        userInfo= new RegisteredUserInfo();

        //verificam daca utilizatorul este deja conectat
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();
                String strName = name.getText().toString();

                if(TextUtils.isEmpty(strEmail)){
                    email.setError("Email cannot be empty!");
                }

                if(TextUtils.isEmpty(strName)){
                    name.setError("Name cannot be empty!");
                }

                if(TextUtils.isEmpty(strPassword)){
                    password.setError("Password cannot be empty!");
                }

                if (strPassword.length() < 8){
                    password.setError("The password needs at least 8 characters!");
                }
                firebaseAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User created successfully.", Toast.LENGTH_SHORT).show();
                            addDatatoFirebase(strEmail, strName);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(strName).build();
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            firebaseUser.updateProfile(profileUpdates);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
    }

    private void addDatatoFirebase(String strEmail, String strName){
        userInfo.setUserEmail(strEmail);
        userInfo.setUserName(strName);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.push().setValue(userInfo);
                Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goLogin(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}