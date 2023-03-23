package Live;
import android.app.NotificationManager;
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

import Firebase.Login;
import Firebase.User;
import Home.History;
import Notif.NotificationHelper;
import com.example.draredebosanci.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Home.Season3;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class LiveRandom extends AppCompatActivity {
    private TextView TVPlayers, TVPlayers2, TVStopWatch,mTextView,mTextView2;
    private EditText timerEditText;
    private CountDownTimer timer;
    private int totalTime = 45 * 60,mCount = 0,mCount2 = 0;
    private Button mButton,mButton2,addTimerButton,bt_Save;
    private List<String> team1,team2;
    private NotificationHelper notificationHelper;
    boolean notificationSent = false;

    DatabaseReference UserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_random);
        notificationHelper = new NotificationHelper(this);
        mTextView = findViewById(R.id.TXT_ScoreTeam1);
        mTextView2 = findViewById(R.id.TXT_ScoreTeam2);
        mButton = findViewById(R.id.bt_Goal1);
        mButton2 = findViewById(R.id.bt_Goal2);
        TVPlayers = findViewById(R.id.TVPlayers);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        addTimerButton = findViewById(R.id.add_timer_button);
        timerEditText = findViewById(R.id.ID_Timer);
        bt_Save = findViewById(R.id.bt_Save);

        setPlayerOnTeam();


        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name = mTextView.getText().toString();
            User user1 = new User(name);
            UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("User");
            UserRef.push().setValue(user1);
            Toast.makeText(LiveRandom.this, "Data Insert", Toast.LENGTH_SHORT).show();
        }
    });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrémentation de l'entier
                mCount++;
                // Mise à jour du TextView
                mTextView.setText(Integer.toString(mCount));
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrémentation de l'entier
                mCount2++;
                // Mise à jour du TextView
                mTextView2.setText(Integer.toString(mCount2));
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        startActivity(new Intent(LiveRandom.this, Season3.class));
    }
    public void goToHistory(View v){
        startActivity(new Intent(LiveRandom.this, History.class));
        Toast.makeText(LiveRandom.this, "Game save", Toast.LENGTH_SHORT).show();

    }



}