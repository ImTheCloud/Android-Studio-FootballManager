package com.example.draredebosanci.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.draredebosanci.R;

public class FormEduard extends AppCompatActivity {

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
        setContentView(R.layout.activity_form_eduard);

        etWin = findViewById(R.id.ETWinEduard);
        etTie = findViewById(R.id.ETTieEduard);
        etLose = findViewById(R.id.ETLoseEduard);
        etYellowCard = findViewById(R.id.ETYellowCardEduard);
        et5Goal = findViewById(R.id.ET5GoalEduard);
        tvPointsWrite = findViewById(R.id.TVPointsWriteEduard);
        tvGameWrite = findViewById(R.id.TVGameWriteEduard);
        tvWinRateWrite = findViewById(R.id.TVWinRateWriteEduard);

        etWin.addTextChangedListener(textWatcher);
        etTie.addTextChangedListener(textWatcher);
        etLose.addTextChangedListener(textWatcher);
        etYellowCard.addTextChangedListener(textWatcher);
        et5Goal.addTextChangedListener(textWatcher);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int counter = prefs.getInt("counterEduard", 10);
        EditText etGoal = findViewById(R.id.ETGoalEduard);
        etGoal.setText(String.valueOf(counter));


    }
// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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