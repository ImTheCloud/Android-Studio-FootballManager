package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.os.Bundle;


public class Notification extends AppCompatActivity {

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        notificationHelper = new NotificationHelper(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        String title = "stopwatch";
        String message = "The time is up";

        NotificationCompat.Builder builder = notificationHelper.createNotification(title, message);

        builder.setSmallIcon(R.drawable.timer);

        NotificationManager manager = notificationHelper.getManager();
        manager.notify(1, builder.build());
    }

}