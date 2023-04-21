package Live;
import static Team.TeamRandom.timerFirst;
import static Team.TeamRandom.timerHalfTime;
import static Team.TeamRandom.timerSecond;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import Home.History;
import Notif.NotificationHelper;
import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Home.Home;
import Team.TeamRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class LiveRandom extends AppCompatActivity {

    private TextView TVPlayers, TVPlayers2, TVStopWatch,goalT1,goalT2;
    private CountDownTimer timer;
    private int totalTime = 45 * 60,mCount = 0,mCount2 = 0;
    private Button mButton,mButton2,bt_Save;
    private List<String> team1,team2;
    private NotificationHelper notificationHelper;
    private Context context;
    private FirebaseAuth mAuth;
    private boolean isActive = false;


    DatabaseReference UserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_random);
        System.out.println(userLocation+"------------------aaaaaaa------------------\n\n\n\n\n\n\n");

        context = this;
        notificationHelper = new NotificationHelper(this);
        goalT1 = findViewById(R.id.TXT_ScoreTeam1);
        goalT2 = findViewById(R.id.TXT_ScoreTeam2);
        mButton = findViewById(R.id.bt_Goal1);
        mButton2 = findViewById(R.id.bt_Goal2);
        TVPlayers = findViewById(R.id.TVPlayers);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        bt_Save = findViewById(R.id.bt_Save);
        isActive = true;

        setPlayerOnTeam();

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseAuth mAuthMail = FirebaseAuth.getInstance();
                FirebaseUser user = mAuthMail.getCurrentUser();
                String userEmail = null;
                if (user != null) {
                    userEmail = user.getEmail();
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference adminRef = database.getReference("Referee");
                String finalUserEmail = userEmail;
                adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Get the list of email addresses from the database
                        ArrayList<String> emailList = new ArrayList<>();
                        for (DataSnapshot emailSnapshot : snapshot.getChildren()) {
                            String email = emailSnapshot.getValue(String.class);
                            emailList.add(email);
                        }
                        // Check if the user's email is in the list
                        if (emailList.contains(finalUserEmail)) {
                            mAuth = FirebaseAuth.getInstance();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String email = currentUser.getEmail();
                            String timerF = timerFirst.getText().toString();
                            String timerHF = timerHalfTime.getText().toString();
                            String timerS = timerSecond.getText().toString();
                            String goalTeam1 = goalT1.getText().toString();
                            String goalTeam2 = goalT2.getText().toString();
                            DatabaseReference UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game");
                            ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int matchCount = (int) dataSnapshot.getChildrenCount() + 1;
                                    String matchId = Integer.toString(matchCount);
                                    System.out.println(userLocation+"------------------aaaaaaa------------------\n\n\n\n\n\n\n");
                                    GameSave game = new GameSave(userLocation,goalTeam1,goalTeam2,timerF,timerS,timerHF,email,date,team2,team1);
                                    UserRef.child(matchId).setValue(game);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            };
                            UserRef.addListenerForSingleValueEvent(valueEventListener);
                            finishTimer();
                            Toast.makeText(LiveRandom.this, "Game saved", Toast.LENGTH_SHORT).show();
                            bt_Save.setEnabled(false);
                        } else {
                            Toast.makeText(getApplicationContext(), "Only the referee can save the game", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String timerHalf = extras.getString("timerHalf");
            int ttimerHalf = (timerHalf != null && !timerHalf.isEmpty()) ? (int) Double.parseDouble(timerHalf) : 0;
            String timerFirst = extras.getString("timerFirst");
            int ttimerFirst = (timerFirst != null && !timerFirst.isEmpty()) ? (int) Double.parseDouble(timerFirst) : 0;
            String timerSecond = extras.getString("timerSecond");
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
        NotificationManager manager = notificationHelper.getManager();
        manager.notify(1, builder.build());
        // Reset the timer
        timer = null;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(LiveRandom.this, TeamRandom.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onDestroy() {
        finishTimer();
        super.onDestroy();
        isActive = false;
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
        for (String player : players) {
            sb.append("\u2022 ").append(player).append("\n");
        }
        textView.setText(sb.toString());
    }

    public void goToHouse(View v){
        finishTimer();
        finish();
        startActivity(new Intent(LiveRandom.this, Home.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void goToHistory(View v){
        finishTimer();
        finish();
        startActivity(new Intent(LiveRandom.this, History.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


}
