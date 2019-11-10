package com.example.hoopfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourtLocationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseRef;
    private static List<Court> courtList;
    MyAdapter md;
    private Location location;
    GoogleApiClient googleApiClient;

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
                Intent launchActivity1 = new Intent(CourtLocationActivity.this, CourtLocationActivity.class);
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




        courtList = new ArrayList<Court>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        md = new MyAdapter(courtList);

        //rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // That's all!

        recyclerView.setAdapter(md);

        testData();
        //readFromDB();


        md.notifyDataSetChanged();

        //String[] test = { "A", "B", "C"};



    }

    public void testData(){
        Court court1 = new Court(1,"North Lawton Playground", 42.349743, -71.127213);
        Court court2 = new Court(1,"Titus Sparrow Park", 42.343640, -71.080235);
        Court court3 = new Court(1,"Peters Park", 42.343150, -71.067338);
        Court court4 = new Court(1,"Ringer Park", 42.350954, -71.138656);

       courtList.add(court1);
       courtList.add(court2);
       courtList.add(court3);
       courtList.add(court4);

       md.notifyDataSetChanged();
    }

    public void readFromDB(){
        DatabaseReference dbCourts = FirebaseDatabase.getInstance().getReference().child("Courts");  // GET COURTS FROM FIREBASE DB
        Log.d("db",FirebaseDatabase.getInstance().getReference().toString());

        ValueEventListener courtListener = new ValueEventListener() {

            //int i = 0;

            // DATABASE CAN ONLY BE READ THROUGH LISTENERS
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // WILL RUN WHEN METHOD IS FIRST RUN AND THEN AGAIN WHENEVER COURTS "TABLE" CHANGES
                    int i =0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Court court = child.getValue(Court.class);
                    //Location courtLocation = new Location("");
                    //courtLocation.setLatitude(court.getLatitude());
                    //courtLocation.setLongitude(court.getLongitude());
                    //float distanceInMeters = courtLocation.distanceTo(location);
                    //Log.d("Court ", court.getName());

                    //if (distanceInMeters < 50) {
                    // NEEDS UPDATE - NEEDS TO SEND NOTIFICATION TO ALL SUBSCRIBED USERS, NOT JUST ONE PHONE NUMBER
                    //sendNotification("17736414066","A user is close to " + court.getName());

                    courtList.add(court);

                    Log.d("Court ", courtList.get(i).getName());
                    i++;
                    //}
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG ", databaseError.toException().toString());
            }

        };

        dbCourts.addValueEventListener(courtListener);

        //return courtList;
    }

}

