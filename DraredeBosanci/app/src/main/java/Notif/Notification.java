package Notif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.os.Bundle;

import com.example.draredebosanci.R;


public class Notification extends AppCompatActivity {

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_random);

        notificationHelper = new NotificationHelper(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        String title = "Stopwatch";
        String message = "Time up";

        NotificationCompat.Builder builder = notificationHelper.createNotification(title);

        builder.setSmallIcon(R.drawable.timer);

        NotificationManager manager = notificationHelper.getManager();
        manager.notify(1, builder.build());
    }

}