package Team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.draredebosanci.R;

import Home.Home;
import Live.LiveSelected;

public class SelectedTeam extends AppCompatActivity{
    private EditText etPlayers1,etPlayers2;
    public static EditText  ttimerFirst,ttimerHalfTime,ttimerSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_team);

        etPlayers1 = findViewById(R.id.ID_Team1);
        etPlayers2 = findViewById(R.id.ID_Team2);
        ttimerFirst = findViewById(R.id.ID_Timer_halftime);
        ttimerHalfTime = findViewById(R.id.ID_Timer_first);
        ttimerSecond = findViewById(R.id.ID_Timer_second);
        Button[] buttons = new Button[] {
                findViewById(R.id.playerClaudiu),
                findViewById(R.id.playerRuben),
                findViewById(R.id.playerDany),
                findViewById(R.id.playerRoberto),
                findViewById(R.id.playerDenis),
                findViewById(R.id.playerLucian),
                findViewById(R.id.playerDavid),
                findViewById(R.id.playerFlavyus),
                findViewById(R.id.playerSimon),
                findViewById(R.id.playerEdaurd),
                findViewById(R.id.playerYaniv),
                findViewById(R.id.playerIosif),
        };

        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!etPlayers1.hasFocus() && !etPlayers2.hasFocus()) {
                        Toast.makeText(getApplicationContext(), "Please select first a team", Toast.LENGTH_SHORT).show();
                        button.setEnabled(true);
                    } else {
                        String player = button.getText().toString();
                        String currentText;
                        String newText;
                        if (etPlayers1.hasFocus()) {
                            currentText = etPlayers1.getText().toString();
                            newText = currentText.isEmpty() ? player : currentText + ", " + player;
                            etPlayers1.setText(newText);
                        } else if (etPlayers2.hasFocus()) {
                            currentText = etPlayers2.getText().toString();
                            newText = currentText.isEmpty() ? player : currentText + ", " + player;
                            etPlayers2.setText(newText);
                        }
                        disableButton(view);
                    }
                }
            });
        }
        final boolean[] isFirstTime = {true};
        etPlayers1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFirstTime[0]) {
                    Toast.makeText(getApplicationContext(), "Please add a comma before a space", Toast.LENGTH_SHORT).show();
                    isFirstTime[0] = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean[] isFirstTime2 = {true};
        etPlayers2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFirstTime2[0]) {
                    Toast.makeText(getApplicationContext(), "Please add a comma before a space", Toast.LENGTH_SHORT).show();
                    isFirstTime2[0] = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        InputFilter numberFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        // Afficher un toast si l'utilisateur entre autre chose qu'un nombre
                        Toast.makeText(getApplicationContext(), "Only number", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };

        ttimerHalfTime.setFilters(new InputFilter[] { numberFilter });
        ttimerFirst.setFilters(new InputFilter[] { numberFilter });
        ttimerSecond.setFilters(new InputFilter[] { numberFilter });

    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void disableButton(View view) {
        Button button = (Button) view;
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }

    public void goToHouse(View v){
        startActivity(new Intent(SelectedTeam.this, Home.class));
    }
    public void goToLiveSelected(View v){

        String timerHalfTimeString = ttimerHalfTime.getText().toString();
        String timerFirstString = ttimerFirst.getText().toString();
        String timerSecondString = ttimerSecond.getText().toString();

        if(timerHalfTimeString.equals("") || timerFirstString.equals("") || timerSecondString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please insert time in each field", Toast.LENGTH_SHORT).show();
            return;
        }

        String team1String1 = etPlayers1.getText().toString();
        String team1String2 = etPlayers2.getText().toString();
        if(team1String1.equals("") || team1String2.equals("") ) {
            Toast.makeText(getApplicationContext(), "Please insert at least one name in each field", Toast.LENGTH_SHORT).show();
            return;
        }



        Intent i = new Intent(SelectedTeam.this, LiveSelected.class);

        String players = etPlayers1.getText().toString();
        i.putExtra("players_data", players);
        String players2 = etPlayers2.getText().toString();
        i.putExtra("players_data2", players2);

        i.putExtra("timerFirst", ttimerFirst.getText().toString());
        i.putExtra("timerHalf", ttimerHalfTime.getText().toString());
        i.putExtra("timerSecond", ttimerSecond.getText().toString());

        startActivity(i);
    }
}
