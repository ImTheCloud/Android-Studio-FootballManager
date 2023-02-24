package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Ranking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
    }

    public void goToClaudiuForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToRubenForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToDenisForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToFlavyusForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToRobertoForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToEduardForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToDavidForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToLucianForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToYanivForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }


}