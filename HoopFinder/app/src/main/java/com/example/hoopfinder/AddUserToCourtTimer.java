package com.example.hoopfinder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.TimerTask;

/**
 * Controls a timer for adding a user to a court's list of users present
 */
public class AddUserToCourtTimer extends TimerTask {
    private final long FIVE_MINUTES = 180000;
    private final long TWENTY_SECONDS = 20000;    // USE FOR TESTING
    String courtName;
    String currentUsersAtCourt;
    User user;
    DatabaseReference db;

    /**
     * Constructor for the AddUserToCourtTimer object
     * @param user The user being added to the court (current user)
     * @param courtName The name of the court the user is being added to
     * @param currentUsersAtCourt A list of users already at the court
     */
    public AddUserToCourtTimer(User user, String courtName, String currentUsersAtCourt){
        this.courtName = courtName;
        this.currentUsersAtCourt = currentUsersAtCourt;
        this.user = user;
    }

    /**
     * Overridden method from the TimerTask class. Puts the thread to sleep for 5 minutes (production)
     * and then adds the user_id to the list of users at the court in the database
     */
    @Override
    public void run() {
        System.out.println("20 second timer has STARTED");
        try {
            Thread.sleep(TWENTY_SECONDS);
        } catch (InterruptedException e) {
            this.cancel();
            e.printStackTrace();
        }
        System.out.println("20 second timer has ENDED");

        db = FirebaseDatabase.getInstance().getReference();
        db.child("Courts").child(courtName).child("usersAtCourt").setValue(currentUsersAtCourt + user.getUser_id());
    }
}
