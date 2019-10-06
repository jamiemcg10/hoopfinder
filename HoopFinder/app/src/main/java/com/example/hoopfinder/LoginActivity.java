package com.example.hoopfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //private LoginViewModel loginViewModel;

    EditText username, password;
    Button loginButton;
    ProgressBar loadingBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory().get(LoginViewModel.class);

        // create variables for the data entered.

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingBar = findViewById(R.id.loading);


        loginButton.setOnClickListener(this);


        /**
         * For Sign in Button
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 3;
                if (username.getText().toString().equals("admin") &&
                        password.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...", Toast.LENGTH_SHORT).show();

                    Intent launchActivity1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(launchActivity1);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    //tx1.setVisibility(View.VISIBLE);
                    //tx1.setBackgroundColor(Color.RED);
                    counter--;
//                    //tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        loginButton.setEnabled(false);
                    }
                }
            }
        });

        /**
         * For Sign up Button
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent launchActivity1 = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(launchActivity1);

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
