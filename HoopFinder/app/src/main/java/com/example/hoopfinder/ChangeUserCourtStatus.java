package com.example.hoopfinder;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.TimerTask;

/**
 * Controls a timer for adding a user to a court's list of users present
 */
public class ChangeUserCourtStatus extends TimerTask {
    private final long FIVE_MINUTES = 180000;
    private final long TWENTY_SECONDS = 20000;    // USE FOR TESTING
    Court court;
    String currentUsersAtCourt;
    User user;
    DatabaseReference db;
    String action;
    Location courtLocation;
    Location userLocation;
    GoogleApiClient googleApiClient;

    /**
     * Constructor for the AddUserToCourtTimer object
     * @param user The user being added to the court (current user)
     * @param court The court that the user is being added to or removed from
     * @param currentUsersAtCourt A list of users already at the court
     */
    public ChangeUserCourtStatus(User user, Court court, String currentUsersAtCourt, String action, GoogleApiClient googleApiClient){
        courtLocation = new Location("");
        this.court = court;
        courtLocation.setLatitude(court.getLatitude());
        courtLocation.setLongitude(court.getLongitude());
        this.currentUsersAtCourt = currentUsersAtCourt;
        this.user = user;
        this.action = action;
        this.googleApiClient = googleApiClient;
    }

    /**
     * Overridden method from the TimerTask class. Puts the thread to sleep for 5 minutes (production)
     * and then adds the user_id to the list of users at the court in the database
     */
    @Override
    public void run() {
        Log.d("ChangeUserCourtStatus","20 second timer has STARTED");
        try {
            Thread.sleep(TWENTY_SECONDS);
        } catch (InterruptedException e) {
            this.cancel();
            e.printStackTrace();
        }
        Log.d("ChangeUserCourtStatus", "20 second timer has ENDED");

        userLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        db = FirebaseDatabase.getInstance().getReference();

        if (this.action=="ADD" & userLocation.distanceTo(courtLocation) < 50) {
            db.child("Courts").child(court.getName()).child("usersAtCourt").setValue(currentUsersAtCourt + user.getUser_id() + ",");
        }

        if (this.action == "REMOVE" & userLocation.distanceTo(courtLocation) >= 50){
            db.child("Courts").child(court.getName()).child("usersAtCourt").setValue(currentUsersAtCourt.replace(user.getUser_id() + ",",""));
        }
    }
}
