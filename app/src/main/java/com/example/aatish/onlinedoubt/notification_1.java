package com.example.aatish.onlinedoubt;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class notification_1 extends Application {
    public static final String CHANNEL_ID = "Online Doubt";
    @Override
    public void onCreate(){
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription( "This is Notification" );

            NotificationManager manager = getSystemService( NotificationManager.class );
            manager.createNotificationChannel( channel );
        }
    }
}
