package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class NewGame extends AppCompatActivity {

    EditText ETPlayers;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        //Var player
        ETPlayers = findViewById(R.id.ID_Player);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        Button btnPlayerClaudiu = findViewById(R.id.playerClaudiu);
        Button btnPlayerRuben = findViewById(R.id.playerRuben);
        Button btnPlayerDany = findViewById(R.id.playerDany );
        Button btnPlayerRoberto = findViewById(R.id.playerRoberto );
        Button btnPlayerDenis = findViewById(R.id.playerDenis );
        Button btnPlayerLucian = findViewById(R.id.playerLucian );
        Button btnPlayerDavid = findViewById(R.id.playerDavid );
        Button btnPlayerFlavyus = findViewById(R.id.playerFlavyus );
        Button btnPlayerEdi = findViewById(R.id.playerEdaurd );
        Button btnPlayerYaniv = findViewById(R.id.playerYaniv );
        Button btnPlayerIosif = findViewById(R.id.playerIosif );
        Button btnPlayerSimon = findViewById(R.id.playerSimon );

        EditText etPlayers = findViewById(R.id.ID_Player);

        btnPlayerSimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerSimon.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });
        btnPlayerIosif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerIosif.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });
        btnPlayerYaniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerYaniv.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });
        btnPlayerEdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerEdi.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });
        btnPlayerFlavyus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerFlavyus.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });
        btnPlayerDavid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerDavid.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });

        btnPlayerLucian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerLucian.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });

        btnPlayerDenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerDenis.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });
        btnPlayerRoberto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerRoberto.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
            }
        });
        btnPlayerClaudiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerClaudiu = btnPlayerClaudiu.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? playerClaudiu : currentText + ", " + playerClaudiu;
                etPlayers.setText(newText);
            }
        });

        btnPlayerDany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerDany = btnPlayerDany.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? playerDany : currentText + ", " + playerDany;
                etPlayers.setText(newText);
            }
        });

        btnPlayerRuben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerRuben = btnPlayerRuben.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? playerRuben : currentText + ", " + playerRuben;
                etPlayers.setText(newText);
            }
        });










/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JANUARY";
        if(month == 2)
            return "FEBRUARY";
        if(month == 3)
            return "MARCH";
        if(month == 4)
            return "APRIL";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUNE";
        if(month == 7)
            return "JULY";
        if(month == 8)
            return "AUGUST";
        if(month == 9)
            return "SEPTEMBER";
        if(month == 10)
            return "OCTOBER";
        if(month == 11)
            return "NOVEMBER";
        if(month == 12)
            return "DECEMBER";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void goToLive(View v){
        Intent i = new Intent(NewGame.this, Live.class);
        i.putExtra("Players", ETPlayers.getText().toString());
        startActivity(i);
    }
}
