package com.example.draredebosanci;

import android.os.Bundle;
import android.text.TextUtils;
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
        if (extras != null) {
            String players = extras.getString("Players");
            String[] playerNames = players.split(",");
            List<String> playerList = new ArrayList<>(Arrays.asList(playerNames));
            Collections.shuffle(playerList);
            int halfSize = playerList.size() / 2;
            List<String> firstHalf = playerList.subList(0, halfSize);
            List<String> secondHalf = playerList.subList(halfSize, playerList.size());
            String playersString = TextUtils.join("\n", firstHalf);
            TVPlayers.setText(playersString);
            playersString = TextUtils.join("\n", secondHalf);
            TVPlayers2.setText(playersString);


            String stopWatch = extras.getString("StopWatch");
            TVStopWatch.setText(stopWatch+" '' ");

        }


    }
}
