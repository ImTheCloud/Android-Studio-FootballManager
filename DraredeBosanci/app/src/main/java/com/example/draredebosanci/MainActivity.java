package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToContact(View v){
        startActivity(new Intent(MainActivity.this, Contact.class));
    }

    public void goToNewGame(View v){
        startActivity(new Intent(MainActivity.this, NewGame.class));
    }

    public void goToOldGame(View v){
        startActivity(new Intent(MainActivity.this, OldGame.class));
    }




}