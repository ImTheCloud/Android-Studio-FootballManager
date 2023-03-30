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


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}

