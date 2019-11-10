package com.example.hoopfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SubscriberListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseRef;
    private List<User> subscriberList;
    SubscriberAdapter md;
    private Location location;
    GoogleApiClient googleApiClient;

    Button courtsTab, subscriberTab, myAccount, mapButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_list);
        //setContentView(R.layout.activity_court_location);

        courtsTab = (Button)findViewById(R.id.courtsTab);
        subscriberTab =(Button)findViewById(R.id.subscriberTab);
        myAccount =(Button)findViewById(R.id.accountTab);
        mapButton =(Button)findViewById(R.id.CourtMap);

        courtsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(SubscriberListActivity.this, CourtLocationActivity.class);
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

        //view = inflater.inflate(R.layout.activity_court_location, container, false);

        subscriberList = new ArrayList<User>();

        recyclerView = (RecyclerView)findViewById(R.id.recylcer_view_user);

        md = new SubscriberAdapter(subscriberList);

        //rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // That's all!

        recyclerView.setAdapter(md);

        testData();

        //readFromDB();
        //String[] test = { "A", "B", "C"};

        //return view;


    }

    public void testData(){
        /* not currently working, need phone numbers in the Constructor() now
        User user1 = new User("1","Saloni@bu.edu","123");
        User user2 = new User("1","Test@bu.edu","123");
        User user3 = new User("1","Mike@bu.edu","123");
        User user4 = new User("1","Jamie@bu.edu","123");

        subscriberList.add(user1);
        subscriberList.add(user2);
        subscriberList.add(user3);
        subscriberList.add(user4);

        md.notifyDataSetChanged();
         */
    }
}
