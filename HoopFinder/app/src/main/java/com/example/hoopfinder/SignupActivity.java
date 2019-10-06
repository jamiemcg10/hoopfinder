package com.example.hoopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button confirm = findViewById(R.id.confirm);
        final EditText emailAdd = findViewById(R.id.emailaddress);
        final EditText password = findViewById(R.id.password);
        final EditText password2 = findViewById(R.id.password2);
        Button cancel = findViewById(R.id.cancel);

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
}
