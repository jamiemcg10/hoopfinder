package com.example.hoopfinder;

// This class will eventually need to check proximity to courts, but for now is just being used
// to confirm that the service is working

// NEEDS TO NOT USE GOOGLE API CLIENT

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.core.content.ContextCompat.checkSelfPermission;


public class ProximityChecker
        implements LocationListener {

    static LocationManager locationManager;
    static Location location;
    String TAG = "ProximityChecker";
    User testUser = new User("jsmart", "jamie@bu.edu", "123456789", "GSU,Walnut Street Park", "");



    public void checkProximity(Context context) {
        while (true) {

            if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.

                Log.d("ProximityChecker", "PERMISSIONS NOT GRANTED");

            }
                Notification notification = new Notification("Hi!", "Still working!");
                notification.sendNotification(context);

                Log.d("Success?", "Yes!");

                if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    Log.d("Lat: ", Double.toString(location.getLatitude()));
                    Log.d("Long: ", Double.toString(location.getLongitude()));
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }

    }

    /**
     * Checks to see if the current user is close to a court
     */
    public void proximityCheck(){

        Log.d("MainActivity", "proximityCheck");
        // CHECK PROXIMITY TO COURTS

        DatabaseReference dbCourts = FirebaseDatabase.getInstance().getReference().child("Courts");  // GET COURTS FROM FIREBASE DB
        ValueEventListener courtListener = new ValueEventListener() {
            // DATABASE CAN ONLY BE READ THROUGH LISTENERS
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // WILL RUN WHEN METHOD IS FIRST RUN AND THEN AGAIN WHENEVER COURTS "TABLE" CHANGES
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Court court = child.getValue(Court.class);
                    Location courtLocation = new Location("");
                    courtLocation.setLatitude(court.getLatitude());
                    courtLocation.setLongitude(court.getLongitude());
                    float distanceInMeters = courtLocation.distanceTo(location);

                    if (distanceInMeters < 50) {
                        // ADD USER TO LIST OF USERS AT COURT
                        // if user not already in list at court
                        /*if (!court.getUsersAtCourt().contains(testUser.getUser_id())) {
                            String currentUsersAtCourt = court.getUsersAtCourt();
                            ChangeUserCourtStatus addTimer = new ChangeUserCourtStatus(testUser, court, currentUsersAtCourt, "ADD", googleApiClient);
                            addTimer.run();
                        }*/
                    }

                    // check if user has left court
                    if (!(court.getUsersAtCourt() == null) && court.getUsersAtCourt().contains(testUser.getUser_id())){
                        if (distanceInMeters >= 50){
                            //user has left court
                            String currentUsersAtCourt = court.getUsersAtCourt();
                            ChangeUserCourtStatus removeTimer = new ChangeUserCourtStatus(testUser, court, currentUsersAtCourt, "REMOVE", googleApiClient);
                            removeTimer.run();
                        }
                    }

                    // check if user has left court
                    if (!(court.getUsersAtCourt() == null) && court.getUsersAtCourt().contains(testUser.getUser_id())){
                        if (distanceInMeters >= 50){
                            //user has left court
                            String currentUsersAtCourt = court.getUsersAtCourt();
                            ChangeUserCourtStatus removeTimer = new ChangeUserCourtStatus(testUser, court, currentUsersAtCourt, "REMOVE", googleApiClient);
                            removeTimer.run();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting a court failed
                Log.w(TAG, "loadCourt:onCancelled", databaseError.toException());

            }
        };

        dbCourts.addValueEventListener(courtListener);

    }

    /**
     * Checks to see if a new user is at a court the user has subscribed to
     */
    public void newUserAtSubscribedCourtCheck(){
        DatabaseReference dbCourts = FirebaseDatabase.getInstance().getReference().child("Courts");  // GET COURTS FROM FIREBASE DB

        ChildEventListener listenerForNewUsersAtCourts = new ChildEventListener() {
            // DATABASE CAN ONLY BE READ THROUGH LISTENERS
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String childName) {
                // WILL RUN WHEN METHOD IS FIRST RUN AND THEN AGAIN WHENEVER COURTS "TABLE" CHANGES
                Court court = dataSnapshot.getValue(Court.class);
                if (testUser.getUser_courtsSubscribedTo().indexOf(court.getName()) >= 0) {   // user subscribed to court that changed
                    //Notification.sendNotification("Court alert!", "A new user is at " + court.getName()+"!");
                }

            }

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

        };

        dbCourts.addChildEventListener(listenerForNewUsersAtCourts);

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
