package com.example.draredebosanci;
import android.content.Intent;
import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collections;
        import java.util.List;
public class Live extends AppCompatActivity {
    private TextView TVPlayers, TVPlayers2, TVStopWatch;
    private CountDownTimer timer;
    private int totalTime = 45 * 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        // Initialize UI elements
        TVPlayers = findViewById(R.id.TVPlayers);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);
        Button addTimerButton = findViewById(R.id.add_timer_button);
        EditText timerEditText = findViewById(R.id.ID_Timer);

        setPlayerOnTeam();


        // Set up timer button click listener
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
                    public void onTick(long millisUntilFinished) {
                        int minutes = (int) millisUntilFinished / 60000;
                        int seconds = (int) (millisUntilFinished % 60000) / 1000;
                        String timeLeft = String.format("%02d:%02d", minutes, seconds);
                        TVStopWatch.setText(timeLeft);
                    }

                    public void onFinish() {
                        TVStopWatch.setText("00:00");
                    }
                }.start();
            }
        });
    }

    public void goToAction(View v){
        startActivity(new Intent(Live.this, Action.class));
    }


    private void setPlayerOnTeam() {

        // Get players from intent extras and split them into two teams
        Bundle extras = getIntent().getExtras();
        String players = extras.getString("Players");
        List<String> playerList = Arrays.asList(players.split(","));
        Collections.shuffle(playerList);
        List<String> team1 = playerList.subList(0, playerList.size() / 2);
        List<String> team2 = playerList.subList(playerList.size() / 2, playerList.size());

        // Set team names and players
        setTeamText(TVPlayers, "Team 1:", team1);
        setTeamText(TVPlayers2, "Team 2:", team2);
    }


    private void setTeamText(TextView textView, String teamName, List<String> players) {
        StringBuilder sb = new StringBuilder(teamName);
        sb.append("\n");
        sb.append(TextUtils.join("\n", players));
        textView.setText(sb.toString());
    }


}
