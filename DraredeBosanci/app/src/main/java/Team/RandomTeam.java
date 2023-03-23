package Team;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.draredebosanci.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Firebase.Game;
import Home.Season3;
import Live.LiveRandom;

public class RandomTeam extends AppCompatActivity {

    private EditText etPlayers, timerFirst,timerHalfTime,timerSecond;
    private LinearLayout linearLayoutBosanci1,linearLayoutBosanci2,linearLayoutHelb1,linearLayoutHelb2;
    private Button playerDareDeBosanci,playerHelb;

    DatabaseReference UserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_team);

         linearLayoutBosanci1 = findViewById(R.id.linearLayoutBosanci1);
         linearLayoutBosanci2 = findViewById(R.id.linearLayoutBosanci2);
         playerDareDeBosanci = findViewById(R.id.bt_drareDeBosanci);
        timerHalfTime = findViewById(R.id.ID_Timer_halftime);
        timerFirst = findViewById(R.id.ID_Timer_first);
        timerSecond = findViewById(R.id.ID_Timer_second);

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






/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        playerDareDeBosanci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutBosanci1.getVisibility() == View.VISIBLE && linearLayoutBosanci2.getVisibility() == View.VISIBLE) {
                    linearLayoutBosanci1.setVisibility(View.INVISIBLE);
                    linearLayoutBosanci2.setVisibility(View.INVISIBLE);
                } else {
                    linearLayoutBosanci1.setVisibility(View.VISIBLE);
                    linearLayoutBosanci2.setVisibility(View.VISIBLE);
                }
            }
        });


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public void goToHouse(View v){
        startActivity(new Intent(RandomTeam.this, Season3.class));
    }

    public void goToLive(View v){


        String timerF = timerFirst.getText().toString();
        String timerHF = timerHalfTime.getText().toString();
        String timerS = timerSecond.getText().toString();
        Game team1 = new Game(timerF,timerHF,timerS);

        UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Time");
        UserRef.push().setValue(team1);


        Intent i = new Intent(RandomTeam.this, LiveRandom.class);
        i.putExtra("Players", etPlayers.getText().toString());

//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.whistle_referee);
//        mediaPlayer.start();

        i.putExtra("timerFirst", timerFirst.getText().toString());
        i.putExtra("timerHalf", timerHalfTime.getText().toString());
        i.putExtra("timerSecond", timerSecond.getText().toString());

        startActivity(i);
    }
}
