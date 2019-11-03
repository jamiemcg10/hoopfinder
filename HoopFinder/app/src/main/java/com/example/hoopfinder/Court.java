/**
 * The Court class holds information to create a structure for basketball courts.
 * It also contains a method for adding a court to the Firebase database
 *
 * @author Jamie Smart
 * @version 1.0
 */

package com.example.hoopfinder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Court {

    private String name;
    private double longitude;
    private double latitude;
    private String subscribers;
    private String usersAtCourt;

    /**
     * Default contructor. Needed for Firebase data reads.
     */
    public Court() {

    }

    /**
     * Constructor to create a court
     *
     * @param name      This is the name of the court
     * @param latitude  This is the latitude of the court
     * @param longitude This is the longitude of the court
     */
    public Court(int id, String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.subscribers = "";
        this.usersAtCourt = "";
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getName() {
        return this.name;
    }

    public String getSubscribers() {
        return this.subscribers;
    }

    public String getUsersAtCourt() { return this.usersAtCourt; }

    /**
     * Adds a court to the database. Illegal characters will be automatically removed from the name
     *
     * @param name      The name of the court
     * @param latitude  The court's latitude
     * @param longitude The court's longitude
     * @return nothing
     */
    public static void addCourt(String name, double latitude, double longitude) {
        DatabaseReference db;

        // remove characters that are incompatable with database
        name = name.replaceAll(Pattern.quote("."), "")
                .replaceAll(Pattern.quote("#"), "")
                .replaceAll(Pattern.quote("$"), "")
                .replaceAll(Pattern.quote("["), "")
                .replaceAll(Pattern.quote("]"), "");


        db = FirebaseDatabase.getInstance().getReference();
        db.child("Courts").child(name).child("name").setValue(name);
        db.child("Courts").child(name).child("latitude").setValue(latitude);
        db.child("Courts").child(name).child("longitude").setValue(longitude);
        db.child("Courts").child(name).child("subscribers").setValue("");
        db.child("Courts").child(name).child("usersAtCourt").setValue("");

    }

    // SUBSCRIBE IMPLEMENTATION CAN CHANGE
    // THIS IS CURRENTLY GETTING SUBSCRIBERS, NOT SUBSCRIBING
    public static boolean subscribeToCourt(String courtName, DataSnapshot snapshot) {
        DatabaseReference db;
        String subscribers = "";

        Court court = snapshot.child(courtName).getValue(Court.class);
        // POSSIBLY NEED TO HANDLE COURT NAME NOT EXISTING
        subscribers = court.getSubscribers();

        System.out.println(subscribers);

        return true;
    }

    /**
     * Adds a set of pre-defined courts to the database
     * @return nothing
     */
    public static void batchAddCourts(){
        addCourt("North Lawton Playground", 42.349743, -71.127213);
        addCourt("Titus Sparrow Park", 42.343640, -71.080235);
        addCourt("Peters Park", 42.343150, -71.067338);
        addCourt("Ringer Park", 42.350954, -71.138656);
        addCourt("Andrew P. Puopolo Junior Athletic Field", 42.368846, -71.054850);
        addCourt("Back Bay Fens", 42.341353, -71.096463);
        addCourt("Coolidge Park", 42.346037, -71.131806);
        addCourt("Joyce Playground", 42.345298, -71.15164);
        addCourt("McLaughlin Playground", 42.328629, -71.103482);
        addCourt("Christopher Lee Playground", 42.337969, -71.031285);
        addCourt("Joe Moakley Park", 42.328269, -71.050589);
        addCourt("Veterans Park", 42.328299, -71.056011);
        addCourt("Clifford Playground", 42.326576, -71.067668);
        addCourt("Orton Field", 42.338357, -71.053460);
        addCourt("Jackson Square Playground", 42.324394, -71.099094);
        addCourt("Marcella Playground", 42.323432, -71.096175);
        addCourt("Jefferson Playground", 42.326548, -71.108667);
        addCourt("Longwood Playground", 42.340168, -71.115721);
        addCourt("Classroom", 42.348775, -71.103703);
    }


}
