package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CompoChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compo_choice);

    }

    public void goTo442(View v){
        startActivity(new Intent(this, Compo442.class));
    }

    public void goTo433(View v){
        startActivity(new Intent(this, Compo433.class));
    }
}