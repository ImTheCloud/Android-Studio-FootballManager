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
import com.google.gson.Gson;

import java.util.Map;

public class History extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTextView = findViewById(R.id.displayHistory);

        // Récupérer une référence à votre base de données Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");

        // Récupérer une référence à l'emplacement "Game" de votre base de données Firebase
        database.getReference("Game").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Convertir les données de la base de données Firebase en un objet Java
                Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                Gson gson = new Gson();
                String json = gson.toJson(data);

                // Afficher les données dans le TextView
                mTextView.setText(json);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer l'erreur
            }
        });
    }
}
