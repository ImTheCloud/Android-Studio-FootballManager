package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Compo433 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compo433);
    }

    public void goTo442(View v){
        startActivity(new Intent(Compo433.this, Compo442.class));
    }

    public void goTo433(View v){
        startActivity(new Intent(Compo433.this, Compo433.class));
    }
}