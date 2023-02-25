package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ChoiceYellowCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_yellow_card);
    }

    public void goToLive(View v){
        onBackPressed(); // Appelle la méthode onBackPressed() de l'activité actuelle
    }
}