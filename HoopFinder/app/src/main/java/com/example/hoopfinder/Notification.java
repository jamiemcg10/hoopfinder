package com.example.hoopfinder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


/**
 * Controls the structure for notifications and allows them to be sent statically
 */
public class Notification {
    // many constants in this class need to be changed
    static String CHANNEL_ID="1"; // perhaps use different channels for different types of notifications

    /**
     * Create a channel for notifications
     */
    private static void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel name"; //getString(R.string.channel_name);
            String description = "Channel description"; // getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = MainActivity.getAppContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Sends notifications through the channel
     * @param notificationTitle The heading/title of the notification
     * @param notificationContent The body text of the notification
     */
    public static void sendNotification(String notificationTitle, String notificationContent) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.hoopfinder_logo)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.getAppContext());

        notificationManager.notify(1, builder.build());  // use unique ID for first arg
    }


}