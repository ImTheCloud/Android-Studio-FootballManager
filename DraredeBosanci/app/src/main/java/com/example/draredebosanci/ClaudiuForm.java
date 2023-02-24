package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
public class ClaudiuForm extends AppCompatActivity {

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
        setContentView(R.layout.activity_claudiu_form);

        // Récupérer les trois EditText et les trois TextViews
        etWin = findViewById(R.id.ETWin);
        etTie = findViewById(R.id.ETTie);
        etLose = findViewById(R.id.ETLose);
        etYellowCard = findViewById(R.id.ETYellowCard);
        et5Goal = findViewById(R.id.ET5Goal);
        tvPointsWrite = findViewById(R.id.TVPointsWrite);
        tvGameWrite = findViewById(R.id.TVGameWrite);
        tvWinRateWrite = findViewById(R.id.TVWinRateWrite);

        // Ajouter les écouteurs d'événements de modification de texte pour chaque EditText
        etWin.addTextChangedListener(textWatcher);
        etTie.addTextChangedListener(textWatcher);
        etLose.addTextChangedListener(textWatcher);
        etYellowCard.addTextChangedListener(textWatcher);
        et5Goal.addTextChangedListener(textWatcher);
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
