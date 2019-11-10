package com.example.hoopfinder;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

public class TestCourtClass {

    public static void main(String[] args){
        TestCourtClass test = new TestCourtClass();
        test.testAddCourt();
    }

    @Test
    public void testAddCourt(){
        // Given - a court name and coordinates
        String courtName = "Test Park Court";
        double latitude = 49.224524;
        double longitude = -72.351215;


        // When - addCourt method is called
        Court.addCourt(courtName, latitude, longitude);


        // Then - court is in db
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Courts");

        ref.equalTo("Test Park Court").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Test
    public void testAddCourtDuplicate(){
        // Given - a duplicate court name
        // When - addCourt method is called
        // Then - court is in db only once

    }

    @Test
    public void testDeleteCourt(){
        // Given - a court name that is in the db
        // When - deleteCourt method is called
        // Then - court is removed from db

    }

}
