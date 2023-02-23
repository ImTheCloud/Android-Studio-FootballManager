package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewGame extends AppCompatActivity {

    EditText ETPlayers, ETStopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        ETPlayers = findViewById(R.id.ID_Player);

    }

    public void goToLive(View v){
        Intent i = new Intent(NewGame.this, Live.class);
        i.putExtra("Players", ETPlayers.getText().toString());


        startActivity(i);
    }
}
