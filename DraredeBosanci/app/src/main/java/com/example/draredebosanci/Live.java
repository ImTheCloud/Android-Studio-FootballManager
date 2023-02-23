package com.example.draredebosanci;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        TVPlayers = findViewById(R.id.TVPlayers);
        TVPlayers2 = findViewById(R.id.TVPlayers2);
        TVStopWatch = findViewById(R.id.TV_StopWatch);


        Bundle extras = getIntent().getExtras();

            String players = extras.getString("Players");
            String[] playerNames = players.split(",");
            List<String> playerList = new ArrayList<>(Arrays.asList(playerNames));
            Collections.shuffle(playerList);
            int halfSize = playerList.size() / 2;
            List<String> firstHalf = playerList.subList(0, halfSize);
            List<String> secondHalf = playerList.subList(halfSize, playerList.size());
        TVPlayers.setText("Team 1:\n");
        String playersString = TextUtils.join("\n", firstHalf);
        TVPlayers.append(playersString);

        TVPlayers2.setText("Team 2:\n");
        playersString = TextUtils.join("\n", secondHalf);
        TVPlayers2.append(playersString);




        final CountDownTimer[] timer = {null};
        final int[] totalTime = {45 * 60}; // Valeur par défaut de 45 minutes

        Button addTimerButton = findViewById(R.id.add_timer_button);
        addTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText timerEditText = findViewById(R.id.ID_Timer);
                String timerString = timerEditText.getText().toString();
                if (!TextUtils.isEmpty(timerString)) {
                    try {
                        int minutes = Integer.parseInt(timerString);
                        totalTime[0] = minutes * 60;
                    } catch (NumberFormatException e) {
                        // La chaîne de caractères n'est pas un entier valide, on utilise la valeur par défaut
                        totalTime[0] = 45*60;
                    }
                }

                // Annuler le minuteur précédent s'il y en a un
                if (timer[0] != null) {
                    timer[0].cancel();
                }

                // Démarrer un nouveau minuteur
                timer[0] = new CountDownTimer(totalTime[0] * 1000, 1000) {
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
}
