package com.example.hoopfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import androidx.annotation.Nullable;
import android.content.pm.PackageManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    // Database reference
    DatabaseReference databaseUsers;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    //private final EditText emailAdd, password, password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button confirm = findViewById(R.id.confirm);
        final EditText emailAdd = findViewById(R.id.emailaddress);
        final EditText password = findViewById(R.id.password);
        final EditText password2 = findViewById(R.id.password2);
        Button cancel = findViewById(R.id.cancel);

       // private Button loginBtn;
       // private ProgressBar progressBar;


        mAuth = FirebaseAuth.getInstance();

        //initializeUI();

        /*confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });*/



        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psswrd1, psswrd2;

                psswrd1= password.getText().toString();
                psswrd2 = password2.getText().toString();

                if (!(psswrd1.equals(psswrd2))) {
                    Log.d(psswrd1, "onClick:password 2: "+ psswrd2);
                    Toast.makeText(getApplicationContext(),
                            "Passwords dont match", Toast.LENGTH_SHORT).show();
                }else{
                    //addUser();

                    registerNewUser();
                    //Toast.makeText(getApplicationContext(),
                           // "Welcome", Toast.LENGTH_SHORT).show();

                   // Intent launchActivity1 = new Intent(SignupActivity.this, MainActivity.class);
                    //startActivity(launchActivity1);
                }

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                emailAdd.setText("");
                password.setText("");
                password2.setText("");


            }
        });
    }

    public String getUserPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            @Nullable String mPhoneNumber = tMgr.getLine1Number(); // todo check permissions for READ PHONE STATE

            if (mPhoneNumber != null) {
                return mPhoneNumber;
            } else {
                return "no phone number, do something instead";
            }
        }
        else {
            Toast.makeText(this, "You need to enable permissions to get phone number!", Toast.LENGTH_SHORT).show();
            return "need permissions";
        }
    }

    private void addUser(){
        EditText email = findViewById(R.id.emailaddress);
        EditText pwd = findViewById(R.id.password);
        String user_email = email.getText().toString().trim();
        String user_pwd = pwd.getText().toString();
        String user_phone_number = getUserPhoneNumber();

        if(!TextUtils.isEmpty(user_email)){

            String user_id = databaseUsers.push().getKey(); //each user will have a unique id

            User user = new User(user_id,user_email,user_pwd,user_phone_number); // pass the user_id user_email user_pwd to the User Object

            databaseUsers.child("users" ).child(user_id).setValue(user);

            Toast.makeText(this, "User registered: " +user_id, Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"You should Enter a valid email", Toast.LENGTH_LONG).show();
        }
    }

    private void registerNewUser() {
        //progressBar.setVisibility(View.VISIBLE);

        EditText emailTV = findViewById(R.id.emailaddress);
        EditText passwordTV = findViewById(R.id.password);

        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        Log.d("Email ", email);
        Log.d("Password ", password);

       /* if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }*/

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d()
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(SignupActivity.this, firebaseAuth.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
                            Log.d("Login Error: ",task.getException().getMessage());
                        }
                    }
                });
    }

    /*private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
    }*/




}
