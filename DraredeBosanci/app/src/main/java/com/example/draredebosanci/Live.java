package com.example.draredebosanci;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Live extends AppCompatActivity {
    private TextView TVPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        TVPlayers = findViewById(R.id.TVPlayers);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String players = extras.getString("Players");
            TVPlayers.setText(players);
        }
    }
}
