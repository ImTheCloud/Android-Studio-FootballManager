package Home;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.draredebosanci.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTextView = findViewById(R.id.displayHistory);

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
                    location += "Lat / long : " + latitude +" "+ longitude + "\n";
                }

                // Extraire les données des scores
                String scores = "";
                for (Map.Entry<String, Object> entry : goalsData.entrySet()) {
                    Map<String, Object> goalData = (Map<String, Object>) entry.getValue();
                    String goalTeam1 = goalData.get("goalTeam1").toString();
                    String goalTeam2 = goalData.get("goalTeam2").toString();
                    scores += "Team 1: " + goalTeam1 + " / Team 2: " + goalTeam2 + "\n";
                }

                // Extraire les données de temps
                String half = "";
                String timeFirstHalf = "";
                String timeSecondHalf = "";
                for (Map.Entry<String, Object> entry : timeData.entrySet()) {
                    Map<String, Object> timePointData = (Map<String, Object>) entry.getValue();
                    half = timePointData.get("half").toString();
                    timeFirstHalf = timePointData.get("timeFirstHalf").toString();
                    timeSecondHalf = timePointData.get("timeSecondHalf").toString();
                }



                // Afficher les données dans le TextView
                String historyData = "Team 1 : " + team1 + "\n" + "Team 2 : " + team2 +  "\n"+ "\n" + "Location : " + location +  "\n"+"\n" + "Scores : " + scores + "\n" + "Time First Half : " + timeFirstHalf + "\n" + "Half : " + half + "\n" + "Time Second Half : " + timeSecondHalf;
                mTextView.setText(historyData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer l'erreur
            }
        });
    }
}

