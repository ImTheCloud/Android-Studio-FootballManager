package Team;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.draredebosanci.R;
import Home.NewGame;
import Home.Home;
import Live.LiveSelected;

public class TeamSelect extends AppCompatActivity{
    private EditText etPlayers1,etPlayers2;
    public static EditText  ttimerFirst,ttimerHalfTime,ttimerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_select);

        etPlayers1 = findViewById(R.id.ID_Team1);
        etPlayers2 = findViewById(R.id.ID_Team2);
        ttimerFirst = findViewById(R.id.ID_Timer_first);
        ttimerHalfTime = findViewById(R.id.ID_Timer_halftime);
        ttimerSecond = findViewById(R.id.ID_Timer_second);

        ttimerFirst.setInputType(InputType.TYPE_CLASS_NUMBER);
        ttimerHalfTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        ttimerSecond.setInputType(InputType.TYPE_CLASS_NUMBER);

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
                findViewById(R.id.playerKami),
                findViewById(R.id.playerMarius),
                findViewById(R.id.playerAlex),
                findViewById(R.id.playerTimote),
                findViewById(R.id.playerBogdan),
                findViewById(R.id.playerVasi),
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
                            newText = currentText.isEmpty() ? player : currentText + "," + player;
                            etPlayers1.setText(newText);
                        } else if (etPlayers2.hasFocus()) {
                            currentText = etPlayers2.getText().toString();
                            newText = currentText.isEmpty() ? player : currentText + "," + player;
                            etPlayers2.setText(newText);
                        }
                        disableButton(view);
                    }
                }
            });
        }
// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public void disableButton(View view) {
        Button button = (Button) view;
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(TeamSelect.this, NewGame.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void goToHouse(View v){
        startActivity(new Intent(TeamSelect.this, Home.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    public void goToLiveSelected(View v){


        String timerFirstString = ttimerFirst.getText().toString();
        String timerHalfTimeString = ttimerHalfTime.getText().toString();
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

        Intent i = new Intent(TeamSelect.this, LiveSelected.class);

        String players = etPlayers1.getText().toString();
        i.putExtra("players_data", players);
        String players2 = etPlayers2.getText().toString();
        i.putExtra("players_data2", players2);

        i.putExtra("timerFirst", ttimerFirst.getText().toString());
        i.putExtra("timerHalf", ttimerHalfTime.getText().toString());
        i.putExtra("timerSecond", ttimerSecond.getText().toString());

        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
