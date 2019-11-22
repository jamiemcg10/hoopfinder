package com.example.hoopfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourtLocationActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;

    public Location currentUserlocation;
    @Nullable
    public User currentUser = null;
    public FirebaseUser firebaseUser;
    public String _uid;

    // mapping user name ->
    public HashMap<String, User> allUsers;
    // mapping court name -> court object if currentUser is subscribed to a given court
    public HashMap<String, Court> subscribedCourts;
    // mapping user name -> user object if currentUser is subscribed to a given user
    public HashMap<String, User> subscribedUsers;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    Button courtsTab, subscriberTab, myAccount, mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_location);

        courtsTab = (Button)findViewById(R.id.courtsTab);
        subscriberTab =(Button)findViewById(R.id.subscriberTab);
        myAccount =(Button)findViewById(R.id.accountTab);
        mapButton =(Button)findViewById(R.id.CourtMap);

        courtsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(CourtLocationActivity.this, SubscribeToCourtActivity.class);
                startActivity(launchActivity1);
            }
        });
        subscriberTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(CourtLocationActivity.this, SubscriberListActivity.class);
                startActivity(launchActivity1);
            }
        });
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(CourtLocationActivity.this, LogoutActivity.class);
                startActivity(launchActivity1);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(CourtLocationActivity.this, AddCourtActivity.class);
                startActivity(launchActivity1);
            }
        });

        // init db ref and do initial read
        databaseRef = FirebaseDatabase.getInstance().getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        _uid = firebaseUser.getUid();

        // populates currentUser attribute
        getCurrentUser();

        // ASSUME WE NOW HAVE THE CURRENT USER


    }

    public void getCurrentUser() {

        /* get current user from DB */
        DatabaseReference userRef = databaseRef.child("User").child(_uid);

        ValueEventListener currentUserListener = new ValueEventListener() {

            // DATABASE CAN ONLY BE READ THROUGH LISTENERS
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG ", databaseError.toException().toString());
            }
        };

        userRef.addListenerForSingleValueEvent(currentUserListener);
    }

    public void getSubscribedCourts() {



        DatabaseReference courtRef = databaseRef.child("Court");

        ValueEventListener courtListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Court court = child.getValue(Court.class);

                    // if court is subscribed to by user
                    subscribedCourts.put(court.getName(), court);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG ", databaseError.toException().toString());
            }
        };
    }

}

