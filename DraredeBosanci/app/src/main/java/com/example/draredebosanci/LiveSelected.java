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

public class LiveSelected extends AppCompatActivity {
    private TextView TVPlayers1, TVPlayers2, TVStopWatch,mTextView,mTextView2;
    private  EditText timerEditText;
    private CountDownTimer timer;
    private int totalTime = 45 * 60,mCount = 0,mCount2 = 0;
    private Button mButton,mButton2,addTimerButton;
    private NotificationHelper notificationHelper;
    boolean notificationSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_selected);

        notificationHelper = new NotificationHelper(this);
        mTextView = findViewById(R.id.TXT_ScoreTeam1);
        mTextView2 = findViewById(R.id.TXT_ScoreTeam2);
        mButton = findViewById(R.id.bt_Goal1);
        mButton2 = findViewById(R.id.bt_Goal2);
        TVPlayers1 = findViewById(R.id.TVPlayers);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        addTimerButton = findViewById(R.id.add_timer_button);
        timerEditText = findViewById(R.id.ID_Timer);
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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String players1 = extras.getString("players_data");
            players1 = players1.replace(",", "\n");
            TVPlayers1.setText(players1);

            String players2 = extras.getString("players_data2");
            players2 = players2.replace(",", "\n");
            TVPlayers2.setText(players2);
        }
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

    public void goToOld(View v){
        startActivity(new Intent(LiveSelected.this, OldGame.class));
    }






}

