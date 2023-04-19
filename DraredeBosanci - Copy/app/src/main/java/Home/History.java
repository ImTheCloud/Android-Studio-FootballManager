package Home;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class History extends AppCompatActivity {
    private TextView teamDisplay,goalDisplay,timeDisplay,dateDisplay,mailDisplay,loadingText;
    private String half,timeFirstHalf,timeSecondHalf,time;
    private Button deleteAll,deleteLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        teamDisplay = findViewById(R.id.Team);
        goalDisplay = findViewById(R.id.Goals);
        timeDisplay = findViewById(R.id.Time);
        dateDisplay = findViewById(R.id.Date);
        mailDisplay = findViewById(R.id.Mail);
        deleteAll = findViewById(R.id.DeleteAll);
        deleteLast = findViewById(R.id.deleteLast);

        loadingText = findViewById(R.id.loading_text);
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
            deleteLast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Only the referee can delete the last game", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            deleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
                    builder.setTitle("Confirmation")
                            .setMessage("Are you sure you want to delete the entire history ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
                                    database.getReference().child("Game").removeValue();

                                    mailDisplay.setVisibility(View.INVISIBLE);
                                    teamDisplay.setVisibility(View.INVISIBLE);
                                    goalDisplay.setVisibility(View.INVISIBLE);
                                    timeDisplay.setVisibility(View.INVISIBLE);
                                    dateDisplay.setVisibility(View.INVISIBLE);
                                    deleteAll.setEnabled(false);
                                    deleteLast.setEnabled(false);

                                    loadingText.setVisibility(View.VISIBLE);
                                    loadingText.setText("No game played");
                                    Toast.makeText(History.this, "All games have been deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            });


            deleteLast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
                    builder.setTitle("Confirmation")
                            .setMessage("Are you sure you want to delete the last game ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");

                                    // Supprimer le dernier élément dans le nœud "Date"
                                    database.getReference().child("Game").child("Date").orderByKey().limitToLast(1).get()
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                    }
                                                }
                                            });

                                    // Supprimer le dernier élément dans le nœud "Goals"
                                    database.getReference().child("Game").child("Goals").orderByKey().limitToLast(1).get()
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                    }
                                                }
                                            });

                                    // Supprimer le dernier élément dans le nœud "Mail"
                                    database.getReference().child("Game").child("Mail").orderByKey().limitToLast(1).get()
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                    }
                                                }
                                            });

                                    // Supprimer le dernier élément dans le nœud "Map"
                                    database.getReference().child("Game").child("Map").orderByKey().limitToLast(1).get()
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                    }
                                                }
                                            });
                                    // Supprimer le dernier élément dans le nœud "Team1"
                                    database.getReference().child("Game").child("Teams").orderByKey().limitToLast(1).get()
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                    }
                                                }
                                            });


                                    // Supprimer le dernier élément dans le nœud "Time"
                                    database.getReference().child("Game").child("Time").orderByKey().limitToLast(1).get()
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                    }
                                                }
                                            });

                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            });

        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");

        database.getReference("Game").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadingText.setVisibility(View.GONE);
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
                    int gameNumber = 1;
                    for (Map.Entry<String, Object> entry : sortedTeamsData.entrySet()) {
                        Map<String, Object> teamData = (Map<String, Object>) entry.getValue();
                        ArrayList<String> players1 = (ArrayList<String>) teamData.get("team1");
                        ArrayList<String> players2 = (ArrayList<String>) teamData.get("team2");
                        team1 += "Game " + gameNumber + " : "+ String.join(", ", players1) + "\n";
                        team2 += "Game " + gameNumber + " : "+  String.join(", ", players2) + "\n";
                        gameNumber++;
                    }

                    String scores = "";
                    gameNumber = 1;
                    for (Map.Entry<String, Object> entry : sortedgoalsData.entrySet()) {
                        Map<String, Object> goalData = (Map<String, Object>) entry.getValue();
                        String goalTeam1 = goalData.get("goalTeam1").toString();
                        String goalTeam2 = goalData.get("goalTeam2").toString();
                        scores += "Game " + gameNumber + " : " + goalTeam1 + " : " + goalTeam2 + "\n";
                        gameNumber++;
                    }

                    String timeDataString="";
                    List<String> times = new ArrayList<>();
                    gameNumber = 1;
                    for (Map.Entry<String, Object> entry : sortedtimeData.entrySet()) {
                        Map<String, Object> timePointData = (Map<String, Object>) entry.getValue();
                        half = timePointData.get("half").toString();
                        timeFirstHalf = timePointData.get("timeFirstHalf").toString();
                        timeSecondHalf = timePointData.get("timeSecondHalf").toString();
                        time = timeFirstHalf + "''  " + half + "''  " + timeSecondHalf + "''";
                        times.add("Game " + gameNumber + " : " + time);
                        timeDataString += "Game " + gameNumber + " : " + time + "\n";
                        gameNumber++;
                    }

                    String dates = "";
                    gameNumber = 1;
                    for (Map.Entry<String, Object> entry : sorteddateData.entrySet()) {
                        Map<String, Object> ddateData = (Map<String, Object>) entry.getValue();
                        String date = ddateData.get("data").toString();
                        dates += "Game " + gameNumber + " : " +  date+ "\n";
                        gameNumber++;
                    }

                    String mails = "";
                    gameNumber = 1;
                    for (Map.Entry<String, Object> entry : sortedgmailData.entrySet()) {
                        Map<String, Object> ddateMail = (Map<String, Object>) entry.getValue();
                        String mail = ddateMail.get("data").toString();
                        if (mail.equals("claudiuppdc7@yahoo.com")) {
                            mail = "Claudiu";
                        }
                        mails += "Game " + gameNumber + " : " +  mail+"\n";
                        gameNumber++;
                    }

                    String teamDisplayData =
                            "Team 1 : " + "\n" + team1 + "\n" +
                                    "Team 2 : "  + "\n"+ team2 +  "\n";
                    String goalDisplayData  = "Scores : " + "\n" + scores;
                    String timeDisplayData  = "Time : " + "\n" + timeDataString;
                    String dateDisplayData  = "Date : " + "\n" + dates;
                    String mailDisplayData = "Referee : " + "\n" + mails;

                    teamDisplay.setText(teamDisplayData);
                    goalDisplay.setText(goalDisplayData);
                    timeDisplay.setText(timeDisplayData);
                    dateDisplay.setText(dateDisplayData);
                    mailDisplay.setText(mailDisplayData);

                }else{
                    loadingText.setVisibility(View.VISIBLE);
                    loadingText.setText("No game played");
                    deleteAll.setEnabled(false);
                    deleteLast.setEnabled(false);
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
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

