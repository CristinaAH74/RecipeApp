package com.recipeapp.recipeapp.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recipeapp.recipeapp.R;
import com.recipeapp.recipeapp.auth.LoginActivity;
import com.recipeapp.recipeapp.home.HomeActivity;
import com.recipeapp.recipeapp.settings.SettingsActivity;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewEmail, textViewName, textViewWelcome;
    private String email, name;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseDatabase = FirebaseDatabase.getInstance("https://recipeapp-95d17-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference("RegisteredUserInfo");

        textViewEmail = findViewById(R.id.textView_show_email);
        textViewName = findViewById(R.id.textView_show_name);
        textViewWelcome = findViewById(R.id.textView_show_welcome);
        user = FirebaseAuth.getInstance().getCurrentUser();

        email = user.getEmail();
        name = user.getDisplayName();

        textViewEmail.setText(email);
        textViewName.setText(name);
        textViewWelcome.setText("Welcome, " + name + "!");

    }

    //Start HomeActivity
    public void goBacktoMain(View view) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    //Start Logout Activity
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    //Start HomeActivity
    public void settings(View view) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        finish();
    }
}