package Home;

import android.os.Bundle;
import android.widget.TextView;
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
    private TextView teamDisplay,goalDisplay,timeDisplay,dateDisplay;
    private String half,timeFirstHalf,timeSecondHalf,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        teamDisplay = findViewById(R.id.Team);
        goalDisplay = findViewById(R.id.Goals);
        timeDisplay = findViewById(R.id.Time);
        dateDisplay = findViewById(R.id.Date);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");

// Récupérer une référence à l'emplacement "Game" de votre base de données Firebase
        database.getReference("Game").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Extraire les données de la base de données Firebase pour les noms des joueurs et la localisation

                Map<String, Object> gameData = (Map<String, Object>) snapshot.getValue();
                Map<String, Object> teamsData = (Map<String, Object>) gameData.get("Teams");
                Map<String, Object> mapData = (Map<String, Object>) gameData.get("Map");
                Map<String, Object> goalsData = (Map<String, Object>) gameData.get("Goals");
                Map<String, Object> timeData = (Map<String, Object>) gameData.get("Time");
                Map<String, Object> dateData = (Map<String, Object>) gameData.get("Date");

                // Extraire les données des noms des joueurs
                String team1 = "";
                String team2 = "";
                for (Map.Entry<String, Object> entry : teamsData.entrySet()) {
                    Map<String, Object> teamData = (Map<String, Object>) entry.getValue();
                    ArrayList<String> players1 = (ArrayList<String>) teamData.get("team1");
                    ArrayList<String> players2 = (ArrayList<String>) teamData.get("team2");
                    team1 += String.join(", ", players1) + "\n";
                    team2 += String.join(", ", players2) + "\n";
                }

                // Extraire les données de la localisation
                String location = "";
                for (Map.Entry<String, Object> entry : mapData.entrySet()) {
                    Map<String, Object> mapPointData = (Map<String, Object>) entry.getValue();
                    Map<String, Object> userLocationData = (Map<String, Object>) mapPointData.get("userLocation");
                    String latitude = userLocationData.get("latitude").toString();
                    String longitude = userLocationData.get("longitude").toString();
                    location += "Lat / long : " + latitude +" / "+ longitude + "\n";
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
                    dates += date;
                }


                String teamDisplayData =
                        "Team 1 : " + "\n" + team1 + "\n" +
                        "Team 2 : "  + "\n"+ team2 +  "\n"+ "\n";
                       // + "Location : "  + "\n"+ location;
                String goalDisplayData  = "Scores : " + "\n" + scores;
                String timeDisplayData  = "Time : " + "\n" + timeDataString;
                String dateDisplayData  = "Date : " + "\n" + dates;


                teamDisplay.setText(teamDisplayData);
                goalDisplay.setText(goalDisplayData);
                timeDisplay.setText(timeDisplayData);
                dateDisplay.setText(dateDisplayData);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer l'erreur
            }
        });
    }
}

