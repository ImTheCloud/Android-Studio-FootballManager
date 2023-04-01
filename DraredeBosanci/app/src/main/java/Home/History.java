package Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Team.NewGame;

public class History extends AppCompatActivity {
    private TextView teamDisplay,goalDisplay,timeDisplay,dateDisplay,mailDisplay;
    private String half,timeFirstHalf,timeSecondHalf,time;
    private Button deleteAll;

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

        TextView loadingText = findViewById(R.id.loading_text);
        loadingText.setVisibility(View.VISIBLE);


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = null;
        if (user != null) {
            userEmail = user.getEmail();
        }

        if(!"claudiuppdc7@yahoo.com".equals(userEmail)){
            deleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Only the referee can delete all games", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            deleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
                    database.getReference().child("Game").removeValue();


                    mailDisplay.setVisibility(View.INVISIBLE);
                    teamDisplay.setVisibility(View.INVISIBLE);
                    goalDisplay.setVisibility(View.INVISIBLE);
                    timeDisplay.setVisibility(View.INVISIBLE);
                    dateDisplay.setVisibility(View.INVISIBLE);


                    loadingText.setVisibility(View.VISIBLE);
                    loadingText.setText("No game played");
                    Toast.makeText(History.this, "All games have been deleted ", Toast.LENGTH_SHORT).show();
                }
            });
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");

        database.getReference("Game").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadingText.setVisibility(View.GONE);

                // Extraire les données de la base de données Firebase pour les noms des joueurs et la localisation
                if(snapshot != null && snapshot.getValue() != null) {
                    teamDisplay.setVisibility(View.VISIBLE);
                    goalDisplay.setVisibility(View.VISIBLE);
                    timeDisplay.setVisibility(View.VISIBLE);
                    dateDisplay.setVisibility(View.VISIBLE);
                    mailDisplay.setVisibility(View.VISIBLE);

                    Map<String, Object> gameData = (Map<String, Object>) snapshot.getValue();
                    Map<String, Object> teamsData = (Map<String, Object>) gameData.get("Teams");
                    Map<String, Object> goalsData = (Map<String, Object>) gameData.get("Goals");
                    Map<String, Object> timeData = (Map<String, Object>) gameData.get("Time");
                    Map<String, Object> dateData = (Map<String, Object>) gameData.get("Date");
                    Map<String, Object> mailData = (Map<String, Object>) gameData.get("Mail");

                    TreeMap<String, Object> sortedTeamsData = new TreeMap<>(teamsData);
                    TreeMap<String, Object> sortedgoalsData = new TreeMap<>(goalsData);
                    TreeMap<String, Object> sortedtimeData = new TreeMap<>(timeData);
                    TreeMap<String, Object> sorteddateData = new TreeMap<>(dateData);
                    TreeMap<String, Object> sortedgmailData = new TreeMap<>(mailData);

                    String team1 = "";
                    String team2 = "";
                    int gameNumber = 1; // initialiser le compteur
                    for (Map.Entry<String, Object> entry : sortedTeamsData.entrySet()) {
                        Map<String, Object> teamData = (Map<String, Object>) entry.getValue();
                        ArrayList<String> players1 = (ArrayList<String>) teamData.get("team1");
                        ArrayList<String> players2 = (ArrayList<String>) teamData.get("team2");
                        team1 += "Game " + gameNumber + " : "+ String.join(", ", players1) + "\n";
                        team2 += "Game " + gameNumber + " : "+  String.join(", ", players2) + "\n";
                        gameNumber++; // incrementer le compteur
                    }

                    String scores = "";
                    gameNumber = 1; // réinitialiser le compteur
                    for (Map.Entry<String, Object> entry : sortedgoalsData.entrySet()) {
                        Map<String, Object> goalData = (Map<String, Object>) entry.getValue();
                        String goalTeam1 = goalData.get("goalTeam1").toString();
                        String goalTeam2 = goalData.get("goalTeam2").toString();
                        scores += "Game " + gameNumber + " : " + goalTeam1 + " : " + goalTeam2 + "\n";
                        gameNumber++; // incrementer le compteur
                    }

                    String timeDataString="";
                    List<String> times = new ArrayList<>();
                    gameNumber = 1; // réinitialiser le compteur
                    for (Map.Entry<String, Object> entry : sortedtimeData.entrySet()) {
                        Map<String, Object> timePointData = (Map<String, Object>) entry.getValue();
                        half = timePointData.get("half").toString();
                        timeFirstHalf = timePointData.get("timeFirstHalf").toString();
                        timeSecondHalf = timePointData.get("timeSecondHalf").toString();
                        time = timeFirstHalf + "''  " + half + "''  " + timeSecondHalf + "''";
                        times.add("Game " + gameNumber + " : " + time);
                        timeDataString += "Game " + gameNumber + " : " + time + "\n";
                        gameNumber++; // incrementer le compteur
                    }

                    String dates = "";
                    gameNumber = 1; // réinitialiser le compteur
                    for (Map.Entry<String, Object> entry : sorteddateData.entrySet()) {
                        Map<String, Object> ddateData = (Map<String, Object>) entry.getValue();
                        String date = ddateData.get("data").toString();
                        dates += "Game " + gameNumber + " : " +  date+ "\n";
                        gameNumber++; // incrementer le compteur
                    }

                    String mails = "";
                    gameNumber = 1; // réinitialiser le compteur
                    for (Map.Entry<String, Object> entry : sortedgmailData.entrySet()) {
                        Map<String, Object> ddateMail = (Map<String, Object>) entry.getValue();
                        String mail = ddateMail.get("data").toString();
                        mails += "Game " + gameNumber + " : " +  mail+"\n";
                        gameNumber++; // incrementer le compteur
                    }




                    String teamDisplayData =
                            "Team 1 : " + "\n" + team1 + "\n" +
                                    "Team 2 : "  + "\n"+ team2 +  "\n";
                    String goalDisplayData  = "Scores : " + "\n" + scores;
                    String timeDisplayData  = "Time : " + "\n" + timeDataString;
                    String dateDisplayData  = "Date : " + "\n" + dates;
                    String mailDisplayData  = "Referee : " + "\n" + mails;


                    teamDisplay.setText(teamDisplayData);
                    goalDisplay.setText(goalDisplayData);
                    timeDisplay.setText(timeDisplayData);
                    dateDisplay.setText(dateDisplayData);
                    mailDisplay.setText(mailDisplayData);


                }else{
                    loadingText.setVisibility(View.VISIBLE);
                    loadingText.setText("No game played");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

