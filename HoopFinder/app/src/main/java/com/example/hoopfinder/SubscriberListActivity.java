package com.example.hoopfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubscriberListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    private ArrayList<User> subscriberList = new ArrayList<>();
    private final String TAG = "SubscriberList";
    SubscriberAdapter md;

    Button courtsTab, subscriberTab, myAccount, mapButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_list);

        courtsTab = (Button)findViewById(R.id.courtsTab);
        subscriberTab =(Button)findViewById(R.id.subscriberTab);
        myAccount =(Button)findViewById(R.id.accountTab);
        mapButton =(Button)findViewById(R.id.CourtMap);

        courtsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(SubscriberListActivity.this, SubscribeToCourtActivity.class);
                startActivity(launchActivity1);
            }
        });
        subscriberTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(SubscriberListActivity.this, SubscriberListActivity.class);
                startActivity(launchActivity1);
            }
        });
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(SubscriberListActivity.this, LogoutActivity.class);
                startActivity(launchActivity1);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(SubscriberListActivity.this, AddCourtActivity.class);
                startActivity(launchActivity1);
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.recylcer_view_user);

        //rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        updateUI();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        userReference = databaseReference.child("Users");

        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                        Log.d(TAG, "DATA EXISTS " + dataSnapshot.toString());
                        User user = dataSnapshot.getValue(User.class);
                        Log.d(TAG, user.toString());
                        subscriberList.add(user);
                        updateUI();
                        // fetchData(dataSnapshot);
                    } else {
                        Log.d(TAG, "WHY NO DATA???");
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Changed",dataSnapshot.getValue(User.class).toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG+"Removed",dataSnapshot.getValue(User.class).toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Moved",dataSnapshot.getValue(User.class).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG+"Cancelled",databaseError.toString());
            }
        });


        
    }

    private void updateUI(){
        mAdapter = new SubscriberAdapter(subscriberList);
        recyclerView.setAdapter(mAdapter);
    }

    public void fetchData(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        subscriberList.add(user);
        updateUI();
    }
}
