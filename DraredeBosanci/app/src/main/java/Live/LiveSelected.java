package Live;
import static Team.TeamSelect.ttimerFirst;
import static Team.TeamSelect.ttimerHalfTime;
import static Team.TeamSelect.ttimerSecond;
import static Home.NewGame.date;
import static Home.NewGame.userLocation;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import Notif.NotificationHelper;
import Home.History;
import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Home.Home;
import Team.TeamSelect;

public class LiveSelected extends AppCompatActivity {
    private TextView TVPlayers1, TVPlayers2, TVStopWatch,goalT1,goalT2;
    private CountDownTimer timer;
    private int totalTime = 45 * 60,mCount = 0,mCount2 = 0;
    private Button mButton,mButton2,bt_Save;
    private NotificationHelper notificationHelper;
    boolean notificationSent = false;
    private Context context;
    private String players1,players2;
    private List<String> listPlayers1,listPlayers2;
    private DatabaseReference UserRef;
    private FirebaseAuth mAuth;
    private boolean isActive = false;
    long startTime = System.currentTimeMillis();
    private long timePaused = 0;
    private long pauseStart = 0;

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


        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LiveSelected.this);
                builder.setMessage("Are you sure you want to save the game now ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mAuth = FirebaseAuth.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();
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
                                            String timerF = ttimerFirst.getText().toString();
                                            String timerHF = ttimerHalfTime.getText().toString();
                                            String timerS = ttimerSecond.getText().toString();
                                            String goalTeam1 = goalT1.getText().toString();
                                            String goalTeam2 = goalT2.getText().toString();
                                            DatabaseReference UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game");
                                            ValueEventListener valueEventListener = new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    int matchCount = (int) dataSnapshot.getChildrenCount() + 1;
                                                    String matchId = Integer.toString(matchCount);
                                                    GameSave game = new GameSave(userLocation,goalTeam1,goalTeam2,timerF,timerS,timerHF,email,date,listPlayers2,listPlayers1);
                                                    UserRef.child(matchId).setValue(game);
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            };
                                            UserRef.addListenerForSingleValueEvent(valueEventListener);
                                            finishTimer();
                                            Toast.makeText(LiveSelected.this, "Game saved", Toast.LENGTH_SHORT).show();
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
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

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
        extractTimerValuesFromIntent();
        startTimer();
    }
    // on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void extractTimerValuesFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String timerHalf = extras.getString("timerHalf");
            int ttimerHalf = (timerHalf != null && !timerHalf.isEmpty()) ? (int) Double.parseDouble(timerHalf) : 0;
            String timerFirst = extras.getString("timerFirst");
            int ttimerFirst = (timerFirst != null && !timerFirst.isEmpty()) ? (int) Double.parseDouble(timerFirst) : 0;
            String timerSecond = extras.getString("timerSecond");
            int ttimerSecond = (timerSecond != null && !timerSecond.isEmpty()) ? (int) Double.parseDouble(timerSecond) : 0;
            int totalTime = ttimerHalf + ttimerFirst + ttimerSecond;
            TVStopWatch.setText(String.valueOf(totalTime));
        }
    }

    private void startTimer() {

        String timerString = TVStopWatch.getText().toString();
        if (!TextUtils.isEmpty(timerString)) {
            try {
                int minutes = Integer.parseInt(timerString);
                totalTime = minutes * 60; // Update totalTime with user input
            } catch (NumberFormatException e) {
                // Invalid input, use default value
            }
        } else {
            // No timer value entered, use default value
            totalTime = 45 * 60;
        }

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
                    String title = "Time left : " + timeLeft;
                    NotificationCompat.Builder builder = notificationHelper.createNotification(title);
                    builder.setSmallIcon(R.drawable.timer);
                    builder.setOnlyAlertOnce(true);
                    NotificationManager manager = notificationHelper.getManager();
                    manager.notify(1, builder.build());
                }
            }

            @Override
            public void onFinish() {
                // Update the timer text
                TVStopWatch.setText("00:00:00");

                // Update the notification title to indicate that the timer has finished
                String title = "Timer finished!";
                NotificationCompat.Builder builder = notificationHelper.createNotification(title);
                builder.setSmallIcon(R.drawable.timer);
                builder.setOnlyAlertOnce(true);
                NotificationManager manager = notificationHelper.getManager();
                manager.notify(1, builder.build());

                // Finish the timer
                finishTimer();
            }
        }.start();
    }

    public void goToPause(View v) {
        Button pauseStartButton = (Button) findViewById(R.id.pauseStart);

        if (timer != null) {
            if (timePaused == 0) { // Timer is not paused
                // Pause the timer
                timer.cancel();
                pauseStart = System.currentTimeMillis();
                timePaused = pauseStart - startTime; // Calculate the correct timePaused value

                // Change the icon of the pauseStart button to "baseline_play_arrow_24"
                pauseStartButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_play_arrow_24, 0, 0, 0);

                // Update the notification with the paused time left value
                String timeLeft = TVStopWatch.getText().toString();
                String title = "Time left : " + timeLeft;
                NotificationCompat.Builder builder = notificationHelper.createNotification(title);
                builder.setSmallIcon(R.drawable.timer);
                builder.setOnlyAlertOnce(true);
                NotificationManager manager = notificationHelper.getManager();
                manager.notify(1, builder.build());
            } else { // Timer is paused
                // Resume the timer
                long remainingTime = (totalTime * 1000) - timePaused;
                timer = new CountDownTimer(remainingTime, 1000) {
                    boolean notificationSent = false; // Initialize notificationSent to false
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (isActive) {
                            // Update the timer
                            int hours = (int) (millisUntilFinished / 3600000);
                            int minutes = (int) ((millisUntilFinished % 3600000) / 60000);
                            int seconds = (int) ((millisUntilFinished % 3600000) % 60000) / 1000;
                            String timeLeft = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                            TVStopWatch.setText(timeLeft);

                            // Update the notification with the resumed time left value
                            String title = "Time left : " + timeLeft;
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
                startTime = System.currentTimeMillis() - timePaused; // Set the correct startTime value
                timePaused = 0;

                // Change the icon of the pauseStart button to "baseline_pause_24"
                pauseStartButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_pause_24, 0, 0, 0);
            }
        } else {
            // Timer is not running, so start it
            extractTimerValuesFromIntent();
            startTime = System.currentTimeMillis(); // Initialize the startTime variable
            startTimer();

            // Change the icon of the pauseStart button to "baseline_pause_24"
            pauseStartButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_pause_24, 0, 0, 0);
        }
    }
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

        if (!notificationSent) {
            String title = "Time up";
            NotificationCompat.Builder builder = notificationHelper.createNotification(title);
            builder.setSmallIcon(R.drawable.timer);
            NotificationManager manager = notificationHelper.getManager();
            manager.notify(1, builder.build());
            notificationSent = true;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(LiveSelected.this, TeamSelect.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    public void goToHouse(View v){
        finishTimer();
        finish();
        startActivity(new Intent(LiveSelected.this, Home.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void goToHistory(View v){
        finishTimer();
        finish();
        startActivity(new Intent(LiveSelected.this, History.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void goToRefresh(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reset the goals to 0?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goalT1.setText("0");
                        goalT2.setText("0");

                        Toast.makeText(getApplicationContext(), "Goals reset", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}