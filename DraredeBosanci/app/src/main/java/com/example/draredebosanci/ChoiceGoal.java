package com.example.draredebosanci;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
public class ChoiceGoal extends AppCompatActivity {
    private TextView[] tvGoals;
    private int[] counters;
    private Button[] buttons;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_goal);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset all counters to 0
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                for (int i = 0; i < tvGoals.length; i++) {
                    counters[i] = 0;
                    editor.putInt("counter" + getPlayerName(i), 0);
                    tvGoals[i].setText("0");
                }
                editor.apply();
            }
        });


        tvGoals = new TextView[]{
                findViewById(R.id.TV_DANY),
                findViewById(R.id.TV_CLAUDIU),
                findViewById(R.id.TV_RUBEN),
                findViewById(R.id.TV_FLAVYUS),
                findViewById(R.id.TV_DENIS),
                findViewById(R.id.TV_ROBERTO),
                findViewById(R.id.TV_LUCIAN),
                findViewById(R.id.TV_DAVID),
                findViewById(R.id.TV_SIMON),
                findViewById(R.id.TV_YANIV),
                findViewById(R.id.TV_EDUARD),
                findViewById(R.id.TV_IOSIF),
        };

        counters = new int[tvGoals.length]; // initialize the counters array

        // Load the saved values from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        for (int i = 0; i < tvGoals.length; i++) {
            String playerName = getPlayerName(i);
            int savedCounter = prefs.getInt("counter" + playerName, 0);
            counters[i] = savedCounter;
            tvGoals[i].setText(String.valueOf(savedCounter));
        }

        buttons = new Button[]{
                findViewById(R.id.BT_Dany),
                findViewById(R.id.BT_Claudiu),
                findViewById(R.id.BT_Ruben),
                findViewById(R.id.BT_Flavyus),
                findViewById(R.id.BT_Denis),
                findViewById(R.id.BT_Roberto),
                findViewById(R.id.BT_Lucian),
                findViewById(R.id.BT_David),
                findViewById(R.id.BT_Simon),
                findViewById(R.id.BT_Yaniv),
                findViewById(R.id.BT_Eduard),
                findViewById(R.id.BT_Iosif),
        };

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counters[index]++;
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("counter" + getPlayerName(index), counters[index]);
                    editor.apply();

                    int currentGoal = Integer.parseInt(tvGoals[index].getText().toString());
                    currentGoal++;
                    tvGoals[index].setText(String.valueOf(currentGoal));
                    onBackPressed();
                }
            });
        }
    }

    private String getPlayerName(int index) {
        switch (index) {
            case 0:
                return "Dany";
            case 1:
                return "Claudiu";
            case 2:
                return "Ruben";
            case 3:
                return "Flavyus";
            case 4:
                return "Denis";
            case 5:
                return "Roberto";
            case 6:
                return "Lucian";
            case 7:
                return "David";
            case 8:
                return "Simon";
            case 9:
                return "Yaniv";
            case 10:
                return "Eduard";
            case 11:
                return "Iosif";
        }
        return null;
    }
}
