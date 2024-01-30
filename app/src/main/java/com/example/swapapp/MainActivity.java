package com.example.swapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String email, password;

    private static final String USERS = "users";
    private static final String TAG = "MainActivity";

    EditText email_input;
    EditText password_input;

    Button login;
    Button signup;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout loginXml = (RelativeLayout) findViewById(R.id.loginXml);
        Snackbar tempWorking = Snackbar.make(loginXml, "Login works", Snackbar.LENGTH_SHORT);
        Snackbar passwordIncorrect = Snackbar.make(loginXml, "Password is incorrect", Snackbar.LENGTH_SHORT);
        Snackbar emailIncorrect = Snackbar.make(loginXml, "Email is incorrect", Snackbar.LENGTH_SHORT);

        email_input = (EditText) findViewById(R.id.email);
        password_input = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("test");

        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_input.getText().toString();
                password = password_input.getText().toString();

                if (email.length() > 0 && password.length() > 0) {
                    loginUser(email, password);
                }
            }
        });

        signup = findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            currentUser.reload();
        }
    }

    public void loginUser(String email, String password) {
        RelativeLayout loginXml = (RelativeLayout) findViewById(R.id.loginXml);
        Snackbar tempWorking = Snackbar.make(loginXml, "Login works", Snackbar.LENGTH_SHORT);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            openNavigationActivity();
                            tempWorking.show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed. Incorrect email/password",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        String keyId = mDatabase.push().getKey();
        mDatabase.setValue(user);
    }

    public void openNavigationActivity() {
        Intent i = new Intent(this, NavigationActivity.class);
        startActivity(i);
    }

    public void openSignUpActivity() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

}
