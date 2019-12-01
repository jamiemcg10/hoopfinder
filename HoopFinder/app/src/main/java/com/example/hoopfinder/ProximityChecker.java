package com.example.hoopfinder;

// This class will eventually need to check proximity to courts, but for now is just being used
// to confirm that the service is working

import android.content.Context;
import android.util.Log;


public class ProximityChecker {


    public static void checkProximity(Context context) {
        while (true) {

            Notification notification = new Notification("Hi!", "Still working!");
            notification.sendNotification(context);

            Log.d("Success?", "Yes!");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
