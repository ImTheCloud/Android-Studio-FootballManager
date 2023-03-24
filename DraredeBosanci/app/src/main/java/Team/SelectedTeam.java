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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Firebase.Game;
import Home.Season3;
import Live.LiveSelected;

public class SelectedTeam extends AppCompatActivity{
    private EditText etPlayers1,etPlayers2;
    public static EditText  ttimerFirst,ttimerHalfTime,ttimerSecond;
    private LinearLayout linearLayoutBosanci1,linearLayoutBosanci2;
    private Button playerDareDeBosanci;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_team);

        linearLayoutBosanci1 = findViewById(R.id.linearLayoutBosanci1);
        linearLayoutBosanci2 = findViewById(R.id.linearLayoutBosanci2);
        playerDareDeBosanci = findViewById(R.id.bt_drareDeBosanci);
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
        ttimerHalfTime.setFilters(new InputFilter[] { numberFilter });
        ttimerFirst.setFilters(new InputFilter[] { numberFilter });
        ttimerSecond.setFilters(new InputFilter[] { numberFilter });









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
