package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class FormClaudiu extends AppCompatActivity {

    private EditText etWin,etTie,etLose,etYellowCard,et5Goal;
    private TextView tvPointsWrite,tvGameWrite,tvWinRateWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_claudiu);

        etWin = findViewById(R.id.ETWinClaudiu);
        etTie = findViewById(R.id.ETTieClaudiu);
        etLose = findViewById(R.id.ETLoseClaudiu);
        etYellowCard = findViewById(R.id.ETYellowCardClaudiu);
        et5Goal = findViewById(R.id.ET5GoalClaudiu);


        tvPointsWrite = findViewById(R.id.TVPointsWriteClaudiu);
        tvGameWrite = findViewById(R.id.TVGameWriteClaudiu);
        tvWinRateWrite = findViewById(R.id.TVWinRateWriteClaudiu);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int counter = prefs.getInt("counterClaudiu", 0);
        EditText etGoal = findViewById(R.id.ETGoalClaudiu);
        etGoal.setText(String.valueOf(counter));


        etWin.setText(prefs.getString("etWin", ""));
        etTie.setText(prefs.getString("etTie", ""));
        etLose.setText(prefs.getString("etLose", ""));
        etYellowCard.setText(prefs.getString("etYellowCard", ""));
        et5Goal.setText(prefs.getString("et5Goal", ""));


    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Ne rien faire avant la modification du texte
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Mettre à jour le calcul lorsque le texte est modifié
            int valueWin = getValue(etWin.getText().toString());
            int valueTie = getValue(etTie.getText().toString());
            int valueLose = getValue(etLose.getText().toString());
            int valueYellowCard = getValue(etYellowCard.getText().toString());
            int bonus5Goal = getValue(et5Goal.getText().toString());
            int totalGames = valueWin + valueTie + valueLose;
            double winRate = totalGames > 0 ? ((double) valueWin / totalGames) * 100 : 0.0;
            int points = ((valueWin * 3) + valueTie + bonus5Goal) - (valueYellowCard / 3);
            tvPointsWrite.setText(String.valueOf(points));
            tvGameWrite.setText(String.valueOf(totalGames));
            tvWinRateWrite.setText(String.format("%.0f%%", winRate));

        }

        @Override
        public void afterTextChanged(Editable s) {
            // Ne rien faire après la modification du texte
        }
    };

    private int getValue(String text) {
        if (text.isEmpty()) {
            return 0; // Valeur par défaut si le champ est vide
        } else {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return 0; // Valeur par défaut si le champ ne contient pas de nombre
            }
        }
    }
}
