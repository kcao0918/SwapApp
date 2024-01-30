package com.example.swapapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    String name, email, password;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private static final String USERS = "users";
    private static final String TAG = "SignUpActivity";

    EditText prompt_name;
    EditText prompt_email;
    EditText prompt_password;

    Button create;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        RelativeLayout signUpXml = (RelativeLayout) findViewById(R.id.signUpXml);

        Snackbar fieldsBlank = Snackbar.make(signUpXml, "Field(s) are blank", Snackbar.LENGTH_SHORT);

        prompt_name = (EditText) findViewById(R.id.prompt_name);
        prompt_email = (EditText) findViewById(R.id.prompt_email);
        prompt_password = (EditText) findViewById(R.id.prompt_password);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();

        create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = prompt_email.getText().toString();
                password = prompt_password.getText().toString();
                name = prompt_name.getText().toString();

                if (name.length() > 0 && email.length() > 0 && password.length() > 0) {
                    user = new User(email, password, name);
                    registerUser(email, password);
                }
                else {
                    fieldsBlank.show();
                }
            }
        });
    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed",
                            Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
