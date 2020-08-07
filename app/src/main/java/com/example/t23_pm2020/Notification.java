package com.example.t23_pm2020;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification extends Application {
    public static final String NOTICE_CHANNEL_1_ID= "noticed";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }
   private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
            NotificationChannel noticed= new NotificationChannel(
                    NOTICE_CHANNEL_1_ID,
                    "report noticed",
                    NotificationManager.IMPORTANCE_HIGH
            );
            noticed.setDescription("MPD noticed notification channel");

            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(noticed);
        }

   }
}
