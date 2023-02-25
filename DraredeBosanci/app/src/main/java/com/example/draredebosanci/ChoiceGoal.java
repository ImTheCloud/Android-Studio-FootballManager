package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChoiceGoal extends AppCompatActivity {

    private Button BT_Claudiu;
    private TextView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_goal);

        BT_Claudiu = findViewById(R.id.BT_Claudiu);
        player = findViewById(R.id.player); // Initialisation du TextView


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("liveActivity")) {
            Live liveActivity = (Live) intent.getSerializableExtra("liveActivity");
            if (liveActivity != null) {
                List<String> team1 = liveActivity.getTeam1();
                if (team1.contains("Claudiu")) {
                    BT_Claudiu.setVisibility(View.VISIBLE);
                } else {
                    BT_Claudiu.setVisibility(View.GONE);
                }
                if (team1.isEmpty()) {
                    player.setText("No players in Team 1");
                } else {
                    player.setText(team1.toString());
                }
            }
        }


    }

    public void goToLive(View v) {
        onBackPressed();
    }
}

