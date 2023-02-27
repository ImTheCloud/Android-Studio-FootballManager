package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Home extends AppCompatActivity {

    String ETPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void goToLive(View v){
        Intent i = new Intent(Home.this, OldGame.class);
        startActivity(i);
    }
    public void goToCompo(View v){
        startActivity(new Intent(Home.this, CompoChoice.class));
    }

    public void goToContact(View v){
        startActivity(new Intent(Home.this, Contact.class));
    }

    public void goToNewGame(View v){
        startActivity(new Intent(Home.this, NewGame.class));
    }

    public void goToOldGame(View v){
        startActivity(new Intent(Home.this, OldGame.class));
    }

    public void goToRanking(View v){
        startActivity(new Intent(Home.this, Form.class));
    }




}