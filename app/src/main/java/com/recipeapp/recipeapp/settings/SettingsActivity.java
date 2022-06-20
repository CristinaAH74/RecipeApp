package com.recipeapp.recipeapp.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.recipeapp.recipeapp.R;
import com.recipeapp.recipeapp.home.HomeActivity;
import com.recipeapp.recipeapp.profile.UserProfileActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView textViewWelcomeSettings;
    private String name;
    FirebaseUser user;
    Button resetPassword, resetEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textViewWelcomeSettings = findViewById(R.id.textView_show_welcome_settings);
        resetPassword = findViewById(R.id.buttonChangePassword);
        resetEmail = findViewById(R.id.buttonChangeEmail);
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();
        textViewWelcomeSettings.setText("Your settings, " + name + "!");

        resetPassword.setOnClickListener((v) -> {
            final EditText resetPass = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Passwrod?");
            passwordResetDialog.setMessage("Enter the new password.");
            passwordResetDialog.setView(resetPass);

            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                //extract the email and send reset link
                String newPassword = resetPass.getText().toString();
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Password changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Password change failed",Toast.LENGTH_SHORT).show();
                    }
                });

            });
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                //close the dialog
            });
            passwordResetDialog.create().show();
        });

        resetEmail.setOnClickListener((v) -> {
            final EditText resetMail = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Email?");
            passwordResetDialog.setMessage("Enter the new Email.");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                //extract the email and send reset link
                String newMail = resetMail.getText().toString();
                user.updateEmail(newMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Email changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Email change failed",Toast.LENGTH_SHORT).show();
                    }
                });

            });
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                //close the dialog
            });
            passwordResetDialog.create().show();
        });
    }

    //Start Profile Activity
    public void goBacktoProfile(View view) {
        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
        finish();
    }
}