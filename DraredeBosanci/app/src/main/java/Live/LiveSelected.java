package Live;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import Firebase.Game;
import Notif.NotificationHelper;
import Home.History;
import com.example.draredebosanci.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import Home.Season3;

public class LiveSelected extends AppCompatActivity {
    private TextView TVPlayers1, TVPlayers2, TVStopWatch,goalT1,goalT2;
    private CountDownTimer timer;
    private int totalTime = 45 * 60,mCount = 0,mCount2 = 0;
    private Button mButton,mButton2,bt_Save;
    private NotificationHelper notificationHelper;
    boolean notificationSent = false;
    private Context context;
    DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_selected);
        context = this;

        notificationHelper = new NotificationHelper(this);
        goalT1 = findViewById(R.id.TXT_ScoreTeam1);
        goalT2 = findViewById(R.id.TXT_ScoreTeam2);
        mButton = findViewById(R.id.bt_Goal1);
        mButton2 = findViewById(R.id.bt_Goal2);
        TVPlayers1 = findViewById(R.id.TVPlayers);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        bt_Save = findViewById(R.id.bt_Save);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String goalTeam1 = goalT1.getText().toString();
                String goalTeam2 = goalT2.getText().toString();
                Game goals = new Game(goalTeam1, goalTeam2);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Goals");
                UserRef.push().setValue(goals);

                Toast.makeText(LiveSelected.this, "Game save", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LiveSelected.this, History.class));
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrémentation de l'entier
                mCount++;

                // Mise à jour du TextView
                goalT1.setText(Integer.toString(mCount));

            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrémentation de l'entier
                mCount2++;

                // Mise à jour du TextView
                goalT2.setText(Integer.toString(mCount2));

            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String players1 = extras.getString("players_data");
            List<String> listPlayers1 = Arrays.asList(players1.split(","));
            String formattedPlayers1 = "";
            for (String player : listPlayers1) {
                formattedPlayers1 += "\u2022 " + player.trim() + "\n";
            }
            TVPlayers1.setText(formattedPlayers1);

            String players2 = extras.getString("players_data2");
            List<String> listPlayers2 = Arrays.asList(players2.split(","));
            String formattedPlayers2 = "";
            for (String player : listPlayers2) {
                formattedPlayers2 += "\u2022 " + player.trim() + "\n";
            }
            TVPlayers2.setText(formattedPlayers2);


            Game teams = new Game(listPlayers1,listPlayers2);
            UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Teams");
            UserRef.push().setValue(teams);

        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Bundle extrass = getIntent().getExtras();
        if (extrass != null) {
            String timerHalf = extrass.getString("timerHalf");
            int ttimerHalf = (timerHalf != null && !timerHalf.isEmpty()) ? (int) Double.parseDouble(timerHalf) : 0;
            String timerFirst = extrass.getString("timerFirst");
            int ttimerFirst = (timerFirst != null && !timerFirst.isEmpty()) ? (int) Double.parseDouble(timerFirst) : 0;
            String timerSecond = extrass.getString("timerSecond");
            int ttimerSecond = (timerSecond != null && !timerSecond.isEmpty()) ? (int) Double.parseDouble(timerSecond) : 0;
            int totalTime = ttimerHalf+ ttimerFirst+ttimerSecond;
            TVStopWatch.setText(String.valueOf(totalTime));
        }

        String timerString = TVStopWatch.getText().toString();
        if (!TextUtils.isEmpty(timerString)) {
            try {
                int minutes = Integer.parseInt(timerString);
                totalTime = minutes * 60;
            } catch (NumberFormatException e) {
                // Invalid input, use default value
                totalTime = 45 * 60;
            }
        }
        // Cancel previous timer if there is one
        if (timer != null) {
            timer.cancel();
        }
        // Start a new timer
        timer = new CountDownTimer(totalTime * 1000, 1000) {
            boolean notificationSent = false; // Initialize notificationSent to false
            @Override
            public void onTick(long millisUntilFinished) {
                int hours = (int) (millisUntilFinished / 3600000);
                int minutes = (int) ((millisUntilFinished % 3600000) / 60000);
                int seconds = (int) ((millisUntilFinished % 3600000) % 60000) / 1000;
                String timeLeft = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                TVStopWatch.setText(timeLeft);



                // Afficher le temps restant dans la notification
                String title = "Temps restant : " + timeLeft;
                NotificationCompat.Builder builder = notificationHelper.createNotification(title);
                builder.setSmallIcon(R.drawable.timer);
                builder.setOnlyAlertOnce(true);

                NotificationManager manager = notificationHelper.getManager();
                manager.notify(1, builder.build());
            }
            @Override
            public void onFinish() {
                TVStopWatch.setText("00:00:00");

                // Display notification when timer finishes
                String title = "Time up";
                NotificationCompat.Builder builder = notificationHelper.createNotification(title);
                builder.setSmallIcon(R.drawable.timer);
                builder.setOnlyAlertOnce(true);

                // Ajouter l'Intent pour ouvrir l'activité "History"
                Intent intent = new Intent(LiveSelected.this, History.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(LiveSelected.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                builder.setContentIntent(pendingIntent);


                NotificationManager manager = notificationHelper.getManager();
                manager.notify(1, builder.build());
                // Reset the timer
                timer = null;
            }


        }.start();
        timer = null;
    }
    // on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        if (timer == null) {
            return;
        }

        // Send notification only if it hasn't been sent before
        if (!notificationSent) {
            String title = "Time up";

            NotificationCompat.Builder builder = notificationHelper.createNotification(title);

            builder.setSmallIcon(R.drawable.timer);

            NotificationManager manager = notificationHelper.getManager();
            manager.notify(1, builder.build());

            // Update notificationSent flag to true
            notificationSent = true;
        }
    }
    public void goToHouse(View v){
        startActivity(new Intent(LiveSelected.this, Season3.class));
    }

    public void goToHistory(View v){
        Toast.makeText(LiveSelected.this, "Game save", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LiveSelected.this, History.class));
    }






}

