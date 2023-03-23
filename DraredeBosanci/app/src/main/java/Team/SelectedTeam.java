package Team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.draredebosanci.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Firebase.Game;
import Home.Season3;
import Live.LiveSelected;

public class SelectedTeam extends AppCompatActivity{
    private EditText etPlayers1,etPlayers2,timerHalfTime,timerFirst,timerSecond;
    private LinearLayout linearLayoutBosanci1,linearLayoutBosanci2;
    private Button playerDareDeBosanci;
    DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_team);

        linearLayoutBosanci1 = findViewById(R.id.linearLayoutBosanci1);
        linearLayoutBosanci2 = findViewById(R.id.linearLayoutBosanci2);
        playerDareDeBosanci = findViewById(R.id.bt_drareDeBosanci);
        etPlayers1 = findViewById(R.id.ID_Team1);
        etPlayers2 = findViewById(R.id.ID_Team2);

        timerHalfTime = findViewById(R.id.ID_Timer_halftime);
        timerFirst = findViewById(R.id.ID_Timer_first);
        timerSecond = findViewById(R.id.ID_Timer_second);

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
        startActivity(new Intent(SelectedTeam.this, Season3.class));
    }
    public void goToLiveSelected(View v){
        String timerF = timerFirst.getText().toString();
        String timerHF = timerHalfTime.getText().toString();
        String timerS = timerSecond.getText().toString();
        Game timeTotal = new Game(timerF,timerHF,timerS);

        UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game/Time");
        UserRef.push().setValue(timeTotal);

        Intent i = new Intent(SelectedTeam.this, LiveSelected.class);
        String players = etPlayers1.getText().toString();
        i.putExtra("players_data", players);
        String players2 = etPlayers2.getText().toString();
        i.putExtra("players_data2", players2);

        i.putExtra("timerFirst", timerFirst.getText().toString());
        i.putExtra("timerHalf", timerHalfTime.getText().toString());
        i.putExtra("timerSecond", timerSecond.getText().toString());

        startActivity(i);
    }
}
