package com.example.draredebosanci;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Arrays;
        import java.util.Collections;
        import java.util.List;
public class LiveRandom extends AppCompatActivity {
    private TextView TVPlayers, TVPlayers2, TVStopWatch;
    private CountDownTimer timer;
    private int totalTime = 45 * 60;
    private int mCount = 0;
    private int mCount2 = 0;
    private TextView mTextView;
    private TextView mTextView2;
    private Button mButton;
    private Button mButton2;
    private List<String> team1;
    private List<String> team2;
    private NotificationHelper notificationHelper;
    boolean notificationSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_random);

        notificationHelper = new NotificationHelper(this);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Récupération des vues
        mTextView = findViewById(R.id.TXT_ScoreTeam1);
        mTextView2 = findViewById(R.id.TXT_ScoreTeam2);
        mButton = findViewById(R.id.bt_Goal1);
        mButton2 = findViewById(R.id.bt_Goal2);

// Définition de l'écouteur de clic pour le bouton "bt_Goal1"
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrémentation de l'entier
                mCount++;

                // Mise à jour du TextView
                mTextView.setText(Integer.toString(mCount));

            }
        });

// Définition de l'écouteur de clic pour le bouton "bt_Goal2"
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrémentation de l'entier
                mCount2++;

                // Mise à jour du TextView
                mTextView2.setText(Integer.toString(mCount2));

            }
        });

        //////////////////////////////////////////////////////////////////////////////
        // Initialize UI elements
        TVPlayers = findViewById(R.id.TVPlayers);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        Button addTimerButton = findViewById(R.id.add_timer_button);
        EditText timerEditText = findViewById(R.id.ID_Timer);

        setPlayerOnTeam();

        addTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timerString = timerEditText.getText().toString();
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
                        int minutes = (int) millisUntilFinished / 60000;
                        int seconds = (int) (millisUntilFinished % 60000) / 1000;
                        String timeLeft = String.format("%02d:%02d", minutes, seconds);
                        TVStopWatch.setText(timeLeft);

                        // Display time left in notification
                        String title = "Time left: " + timeLeft;

                        NotificationCompat.Builder builder = notificationHelper.createNotification(title);
                        builder.setSmallIcon(R.drawable.timer);
                        builder.setOnlyAlertOnce(true);

                        NotificationManager manager = notificationHelper.getManager();
                        manager.notify(1, builder.build());
                    }


                    @Override
                    public void onFinish() {
                        TVStopWatch.setText("00:00");

                        // Display notification when timer finishes
                        String title = "Time up";

                        NotificationCompat.Builder builder = notificationHelper.createNotification(title);

                        builder.setSmallIcon(R.drawable.timer);
                        builder.setOnlyAlertOnce(true);

                        NotificationManager manager = notificationHelper.getManager();
                        manager.notify(1, builder.build());

                        // Reset the timer
                        timer = null;
                    }

                }.start();
            }
        });

        // Initialize the timer to null to prevent it from triggering a notification on startup
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

    private void setPlayerOnTeam() {
        // Get players from intent extras and split them into two teams
        Bundle extras = getIntent().getExtras();
        String players = extras.getString("Players");
        List<String> playerList = Arrays.asList(players.split(","));
        Collections.shuffle(playerList);
        team1 = playerList.subList(0, playerList.size() / 2);
        team2 = playerList.subList(playerList.size() / 2, playerList.size());
        // Set team names and players
        setTeamText(TVPlayers, team1);
        setTeamText(TVPlayers2, team2);
    }

    private void setTeamText(TextView textView, List<String> players) {
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.join("\n", players));
        textView.setText(sb.toString());
    }



    public void goToChoiceGoal(View v){
        startActivity(new Intent(LiveRandom.this, ChoiceGoal.class));
//        MediaPlayer mediaPlayer = MediaPlayer.create(Live.this, R.raw.whistle_referee);
//        mediaPlayer.start();
    }

    public void goToHouse(View v){
        startActivity(new Intent(LiveRandom.this, Home.class));
    }

    public void goToOld(View v){
        startActivity(new Intent(LiveRandom.this, OldGame.class));
    }






}

