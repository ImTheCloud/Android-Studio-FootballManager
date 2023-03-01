package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class FormDany extends AppCompatActivity {

    private EditText etWin;
    private EditText etTie;
    private EditText etLose;
    private EditText etYellowCard;
    private EditText et5Goal;
    private TextView tvPointsWrite;
    private TextView tvGameWrite;
    private TextView tvWinRateWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_dany);

        etWin = findViewById(R.id.ETWinDany);
        etTie = findViewById(R.id.ETTieDany);
        etLose = findViewById(R.id.ETLoseDany);
        etYellowCard = findViewById(R.id.ETYellowCardDany);
        et5Goal = findViewById(R.id.ET5GoalDany);
        tvPointsWrite = findViewById(R.id.TVPointsWriteDany);
        tvGameWrite = findViewById(R.id.TVGameWriteDany);
        tvWinRateWrite = findViewById(R.id.TVWinRateWriteDany);

        etWin.addTextChangedListener(textWatcher);
        etTie.addTextChangedListener(textWatcher);
        etLose.addTextChangedListener(textWatcher);
        etYellowCard.addTextChangedListener(textWatcher);
        et5Goal.addTextChangedListener(textWatcher);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int counter = prefs.getInt("counterDany", 0);
        EditText etGoal = findViewById(R.id.ETGoalDany);
        etGoal.setText(String.valueOf(counter));
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