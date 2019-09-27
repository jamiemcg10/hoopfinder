package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.notify.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /** SEND A TEXT NOTIFICATION */
    public void notifyUser(View view){
        /** NOTIFICATION BUILDER */
        System.out.println("made it!");
        Intent intent = new Intent(this, AlertDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")   // CHANNEL ID SHOULD BE CONSTANT
                .setSmallIcon(R.drawable.hoopimg)
                .setContentTitle("HoopFinder")
                .setContentText("This is a notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        String messageText = "This is a HoopFinder notification!";
        //Intent intent = new Intent(this, NotifyActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }





    /** CREATE A NOTIFICATION CHANNEL - NEEDED FOR ANDROID 8.0+ */
    private void createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "channel name";               // getString(R.string.channel_name)
            String description = "description";      // getString(R.string.channel_description)
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("General", name, importance);     // CHANNEL ID USED TO BE A CONSTANT
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
