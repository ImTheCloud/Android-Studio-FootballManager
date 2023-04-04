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

import Home.Home;
import Live.LiveRandom;
import Home.NewGame;
import Ranking.RankClaudiu;

public class TeamRandom extends AppCompatActivity {

    private EditText etPlayers;

    public static EditText  timerFirst,timerHalfTime,timerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_random);


        timerHalfTime = findViewById(R.id.ID_Timer_halftime);
        timerFirst = findViewById(R.id.ID_Timer_first);
        timerSecond = findViewById(R.id.ID_Timer_second);

        timerHalfTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        timerFirst.setInputType(InputType.TYPE_CLASS_NUMBER);
        timerSecond.setInputType(InputType.TYPE_CLASS_NUMBER);


        etPlayers = findViewById(R.id.ID_Player);
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
                    String player = button.getText().toString();
                    String currentText = etPlayers.getText().toString();
                    String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                    etPlayers.setText(newText);
                    disableButton(view);
                }
            });
        }


    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void disableButton(View view) {
        Button button = (Button) view;
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(TeamRandom.this, NewGame.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToHouse(View v){
        startActivity(new Intent(TeamRandom.this, Home.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void goToLive(View v) {
        String timerHalfTimeString = timerHalfTime.getText().toString();
        String timerFirstString = timerFirst.getText().toString();
        String timerSecondString = timerSecond.getText().toString();

        if(timerHalfTimeString.equals("") || timerFirstString.equals("") || timerSecondString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please insert time in each field", Toast.LENGTH_SHORT).show();
            return;
        }

        String team1String = etPlayers.getText().toString();
        String[] players = team1String.split(",");

        if(players.length < 2) {
            Toast.makeText(getApplicationContext(), "Please insert at least two player names", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(TeamRandom.this, LiveRandom.class);
        i.putExtra("Players", etPlayers.getText().toString());
        i.putExtra("timerFirst", timerFirstString);
        i.putExtra("timerHalf", timerHalfTimeString);
        i.putExtra("timerSecond", timerSecondString);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
