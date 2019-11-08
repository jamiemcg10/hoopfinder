package com.example.hoopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        Timer t = new Timer();

        t.schedule(new TimerTask() {
            @Override
            public void run() {

                Intent launchActivity1 = new Intent(StartingActivity.this, CourtLocationActivity.class);
                startActivity(launchActivity1);
            }
        },5000);
    }
}