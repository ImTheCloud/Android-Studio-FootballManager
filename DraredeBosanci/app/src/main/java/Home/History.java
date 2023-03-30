package Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.draredebosanci.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {
    private TextView teamDisplay,goalDisplay,timeDisplay,dateDisplay,mailDisplay;
    private String half,timeFirstHalf,timeSecondHalf,time;
    private Button deleteAll,deleteLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        teamDisplay = findViewById(R.id.Team);
        goalDisplay = findViewById(R.id.Goals);
        timeDisplay = findViewById(R.id.Time);
        dateDisplay = findViewById(R.id.Date);
        mailDisplay = findViewById(R.id.Mail);

        deleteAll = findViewById(R.id.DeleteAll);
        deleteLast = findViewById(R.id.deleteLast);

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
                database.getReference().setValue(null); // Supprime toutes les données de la base de données Firebase
                // Mettre à jour l'interface utilisateur pour afficher que toutes les données ont été supprimées
                teamDisplay.setText("");
                goalDisplay.setText("");
                timeDisplay.setText("");
                dateDisplay.setText("");
                Toast.makeText(History.this, "Toutes les données ont été supprimées", Toast.LENGTH_SHORT).show();
            }
        });

        deleteLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(History.this, "Rien lol", Toast.LENGTH_SHORT).show();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");

        database.getReference("Game").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Extraire les données de la base de données Firebase pour les noms des joueurs et la localisation
                if(snapshot != null && snapshot.getValue() != null) {
                    Map<String, Object> gameData = (Map<String, Object>) snapshot.getValue();
                    Map<String, Object> teamsData = (Map<String, Object>) gameData.get("Teams");
                    Map<String, Object> goalsData = (Map<String, Object>) gameData.get("Goals");
                    Map<String, Object> timeData = (Map<String, Object>) gameData.get("Time");
                    Map<String, Object> dateData = (Map<String, Object>) gameData.get("Date");
                    Map<String, Object> mailData = (Map<String, Object>) gameData.get("Mail");

                    // Extraire les données des noms des joueurs
                    String team1 = "";
                    String team2 = "";
                    for (Map.Entry<String, Object> entry : teamsData.entrySet()) {
                        Map<String, Object> teamData = (Map<String, Object>) entry.getValue();
                        ArrayList<String> players1 = (ArrayList<String>) teamData.get("team1");
                        ArrayList<String> players2 = (ArrayList<String>) teamData.get("team2");
                        team2 += String.join(", ", players2) + "\n";
                        team1 += String.join(", ", players1) + "\n";
                    }



                    // Extraire les données des scores
                    String scores = "";
                    for (Map.Entry<String, Object> entry : goalsData.entrySet()) {
                        Map<String, Object> goalData = (Map<String, Object>) entry.getValue();
                        String goalTeam1 = goalData.get("goalTeam1").toString();
                        String goalTeam2 = goalData.get("goalTeam2").toString();
                        scores += goalTeam1 + " : " + goalTeam2 + "\n";
                    }

                    // Extraire les données de temps
                    List<String> times = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : timeData.entrySet()) {
                        Map<String, Object> timePointData = (Map<String, Object>) entry.getValue();
                        half = timePointData.get("half").toString();
                        timeFirstHalf = timePointData.get("timeFirstHalf").toString();
                        timeSecondHalf = timePointData.get("timeSecondHalf").toString();
                        time = timeFirstHalf + "'' " + half + "'' " + timeSecondHalf + "''";
                        times.add(time);
                    }
                    String timeDataString = String.join("\n", times);


                    String dates = "";
                    for (Map.Entry<String, Object> entry : dateData.entrySet()) {
                        Map<String, Object> ddateData = (Map<String, Object>) entry.getValue();
                        String date = ddateData.get("data").toString();
                        dates += date+ "\n";
                    }

                    String mails = "";
                    for (Map.Entry<String, Object> entry : mailData.entrySet()) {
                        Map<String, Object> ddateMail = (Map<String, Object>) entry.getValue();
                        String mail = ddateMail.get("data").toString();
                        mails += mail+ "\n";
                    }


                    String teamDisplayData =
                            "Team 1 : " + "\n" + team1 + "\n" +
                                    "Team 2 : "  + "\n"+ team2 +  "\n"+ "\n";
                    String goalDisplayData  = "Scores : " + "\n" + scores;
                    String timeDisplayData  = "Time : " + "\n" + timeDataString;
                    String dateDisplayData  = "Date : " + "\n" + dates;
                    String mailDisplayData  = "Mail : " + "\n" + mails;


                    teamDisplay.setText(teamDisplayData);
                    goalDisplay.setText(goalDisplayData);
                    timeDisplay.setText(timeDisplayData);
                    dateDisplay.setText(dateDisplayData);
                    mailDisplay.setText(mailDisplayData);


                }   }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer l'erreur
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}

