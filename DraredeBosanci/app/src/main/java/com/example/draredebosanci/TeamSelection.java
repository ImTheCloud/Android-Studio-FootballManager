package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TeamSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
    }

    public void goToRandomTeam(View v){
        startActivity(new Intent(TeamSelection.this, RandomTeam.class));
    }
    public void goToSelectedTeam(View v){
        startActivity(new Intent(TeamSelection.this, SelectedTeam.class));
    }



}