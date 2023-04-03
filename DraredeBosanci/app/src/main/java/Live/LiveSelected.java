package Live;
import static Team.TeamSelect.ttimerFirst;
import static Team.TeamSelect.ttimerHalfTime;
import static Team.TeamSelect.ttimerSecond;
import static Home.NewGame.date;
import static Home.NewGame.userLocation;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import Firebase.Game;
import Notif.NotificationHelper;
import Home.History;
import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import Home.Home;

public class LiveSelected extends AppCompatActivity {
    private TextView TVPlayers1, TVPlayers2, TVStopWatch,goalT1,goalT2;
    private CountDownTimer timer;
    private int totalTime = 45 * 60,mCount = 0,mCount2 = 0;
    private Button mButton,mButton2,bt_Save;
    private NotificationHelper notificationHelper;
    boolean notificationSent = false;
    private Context context;
    String players1,players2;
    List<String> listPlayers1,listPlayers2;
    DatabaseReference UserRef;
    private FirebaseAuth mAuth;
    private boolean isActive = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_selected);
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
        isActive = true;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseAuth mAuthMail = FirebaseAuth.getInstance();
                mAuthMail = FirebaseAuth.getInstance();
                FirebaseUser user = mAuthMail.getCurrentUser();
                String userEmail = null;
                if (user != null) {
                    userEmail = user.getEmail();
                }

                if(!"claudiuppdc7@yahoo.com".equals(userEmail)){
                    Toast.makeText(getApplicationContext(), "Only the referee can save game", Toast.LENGTH_SHORT).show();
                }else{
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String email = currentUser.getEmail();
                Game user_mail = new Game(email);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Mail");
                UserRef.push().setValue(user_mail);

                Game dateUser = new Game(date);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Date");
                UserRef.push().setValue(dateUser);

                Game map = new Game(userLocation);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Map");
                UserRef.push().setValue(map);

                String timerF = ttimerFirst.getText().toString();
                String timerHF = ttimerHalfTime.getText().toString();
                String timerS = ttimerSecond.getText().toString();
                Game timeTotal = new Game(timerF,timerHF,timerS);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Time");
                UserRef.push().setValue(timeTotal);

                Game teams = new Game(listPlayers1,listPlayers2);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Teams");
                UserRef.push().setValue(teams);


                String goalTeam1 = goalT1.getText().toString();
                String goalTeam2 = goalT2.getText().toString();
                Game goals = new Game(goalTeam1, goalTeam2);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Goals");
                UserRef.push().setValue(goals);
                finishTimer();
                finish();

                Toast.makeText(LiveSelected.this, "Game save", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LiveSelected.this, History.class));
                }
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
            players1 = extras.getString("players_data");
            listPlayers1 = Arrays.asList(players1.split(","));
            String formattedPlayers1 = "";
            for (String player : listPlayers1) {
                formattedPlayers1 += "\u2022 " + player.trim() + "\n";
            }
            TVPlayers1.setText(formattedPlayers1);

            players2 = extras.getString("players_data2");
            listPlayers2 = Arrays.asList(players2.split(","));
            String formattedPlayers2 = "";
            for (String player : listPlayers2) {
                formattedPlayers2 += "\u2022 " + player.trim() + "\n";
            }
            TVPlayers2.setText(formattedPlayers2);




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
                if (isActive) {
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
            }
            @Override
            public void onFinish() {
                finishTimer();

            }


        }.start();
        timer = null;
    }
    // on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void finishTimer() {
        TVStopWatch.setText("00:00:00");
        // Display notification when timer finishes
        String title = "Finished Game";
        NotificationCompat.Builder builder = notificationHelper.createNotification(title);
        builder.setSmallIcon(R.drawable.timer);
        builder.setOnlyAlertOnce(true);
        // Ajouter l'Intent pour ouvrir l'activité "History"
//        Intent intent = new Intent(LiveSelected.this, History.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(LiveSelected.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        builder.setContentIntent(pendingIntent);
        NotificationManager manager = notificationHelper.getManager();
        manager.notify(1, builder.build());
        // Reset the timer
        timer = null;

    }

    @Override
    protected void onDestroy() {
        finishTimer();
        super.onDestroy();
        isActive = false;
    }


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
        finishTimer();
        finish();
        startActivity(new Intent(LiveSelected.this, Home.class));
    }
}