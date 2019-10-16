package com.example.hoopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    // Database reference
    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button confirm = findViewById(R.id.confirm);
        final EditText emailAdd = findViewById(R.id.emailaddress);
        final EditText password = findViewById(R.id.password);
        final EditText password2 = findViewById(R.id.password2);
        Button cancel = findViewById(R.id.cancel);


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
                    addUser();
                    Toast.makeText(getApplicationContext(),
                            "Welcome", Toast.LENGTH_SHORT).show();

                    Intent launchActivity1 = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(launchActivity1);
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

    private void addUser(){
        EditText email = findViewById(R.id.emailaddress);
        EditText pwd = findViewById(R.id.password);
        String user_email = email.getText().toString().trim();
        String user_pwd = pwd.getText().toString();

        if(!TextUtils.isEmpty(user_email)){

            String user_id = databaseUsers.push().getKey(); //each user will have a unique id

            User user = new User(user_id,user_email,user_pwd); // pass the user_id user_email user_pwd to the User Object

            databaseUsers.child("users" ).child(user_id).setValue(user);

            Toast.makeText(this, "User registered: " +user_id, Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"You should Enter a valid email", Toast.LENGTH_LONG).show();
        }
    }
}
