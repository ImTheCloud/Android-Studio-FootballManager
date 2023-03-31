package Team;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.draredebosanci.R;

import Home.Home;
import Live.LiveRandom;

public class RandomTeam extends AppCompatActivity {

    private EditText etPlayers;

    public static EditText  timerFirst,timerHalfTime,timerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_team);


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


        // Créer un InputFilter qui n'accepte que les nombres
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

// Attacher le InputFilter aux champs de texte
        timerHalfTime.setFilters(new InputFilter[] { numberFilter });
        timerFirst.setFilters(new InputFilter[] { numberFilter });
        timerSecond.setFilters(new InputFilter[] { numberFilter });


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
        startActivity(new Intent(RandomTeam.this, Home.class));
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

        Intent i = new Intent(RandomTeam.this, LiveRandom.class);
        i.putExtra("Players", etPlayers.getText().toString());
        i.putExtra("timerFirst", timerFirstString);
        i.putExtra("timerHalf", timerHalfTimeString);
        i.putExtra("timerSecond", timerSecondString);
        startActivity(i);
    }


}
