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
    private TextView teamDisplay,loadingText;

    private Button deleteAll,deleteLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        teamDisplay = findViewById(R.id.Team);
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

        if (!"claudiuppdc7@yahoo.com".equals(userEmail)) {
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
        } else {
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

                                    teamDisplay.setVisibility(View.INVISIBLE);

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
                if (snapshot != null && snapshot.getValue() != null) {

                    teamDisplay.setVisibility(View.VISIBLE);
                    StringBuilder gameData = new StringBuilder();
                    int gameNumber = 1;

                    for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
                        if (gameSnapshot.getValue() != null) {
                            String dataText = gameSnapshot.child("data").getValue(String.class);
                            String goal1 = gameSnapshot.child("goalTeam1").getValue(String.class);
                            String goal2 = gameSnapshot.child("goalTeam2").getValue(String.class);
                            String halfText = gameSnapshot.child("half").getValue(String.class);
                            String timeFirstHalf = gameSnapshot.child("timeFirstHalf").getValue(String.class);
                            String timeSecondHalf = gameSnapshot.child("timeSecondHalf").getValue(String.class);
                            String email = gameSnapshot.child("email").getValue(String.class);
                            if (email.equals("claudiuppdc7@yahoo.com")) {
                                email = "Claudiu";
                            }

                            gameData.append("Game ");
                            gameData.append(gameNumber);
                            gameData.append(" : ");
                            gameData.append("\n");
                            gameData.append("Referee : ");
                            gameData.append(email);
                            gameData.append("\n");
                            gameData.append("Date : ");
                            gameData.append(dataText);
                            gameData.append("\n");
                            gameData.append("Team 1 : ");
                            gameData.append(gameSnapshot.child("team1").getValue());
                            gameData.append("\n");
                            gameData.append("Team 2 : ");
                            gameData.append(gameSnapshot.child("team2").getValue());
                            gameData.append("\n");
                            gameData.append("Score : ");
                            gameData.append(goal1);
                            gameData.append(" - ");
                            gameData.append(goal2);
                            gameData.append("\n");
                            gameData.append("Time : ");
                            gameData.append(timeFirstHalf);
                            gameData.append("\" ");
                            gameData.append(halfText);
                            gameData.append("\" ");
                            gameData.append(timeSecondHalf);
                            gameData.append("\"");
                            gameData.append("\n\n");

                            gameNumber++;

                    }
                    }

                    teamDisplay.setText(gameData.toString());



            } else {
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

