package Compo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.draredebosanci.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Compo442 extends AppCompatActivity {
    private EditText player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11;
    private Button bt_Save;
    private DatabaseReference UserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compo442);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        player5 = findViewById(R.id.player5);
        player6 = findViewById(R.id.player6);
        player7 = findViewById(R.id.player7);
        player8 = findViewById(R.id.player8);
        player9 = findViewById(R.id.player9);
        player10 = findViewById(R.id.player10);
        player11 = findViewById(R.id.player11);
        bt_Save = findViewById(R.id.bt_Save);

        EditText[] editTextArray = new EditText[] {

                findViewById(R.id.player1),
        findViewById(R.id.player2),
        findViewById(R.id.player3),
        findViewById(R.id.player4),
        findViewById(R.id.player5),
        findViewById(R.id.player6),
        findViewById(R.id.player7),
        findViewById(R.id.player8),
        findViewById(R.id.player9),
        findViewById(R.id.player10),
        findViewById(R.id.player11)
        };

        for (EditText editText : editTextArray) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        ((EditText) view).setText("");
                    }
                }
            });
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
        database.getReference("Compo").child("-compo442").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pl1 = snapshot.child("player1").getValue(String.class);
                    String pl2 = snapshot.child("player2").getValue(String.class);
                    String pl3 = snapshot.child("player3").getValue(String.class);
                    String pl4 = snapshot.child("player4").getValue(String.class);
                    String pl5 = snapshot.child("player5").getValue(String.class);
                    String pl6 = snapshot.child("player6").getValue(String.class);
                    String pl7 = snapshot.child("player7").getValue(String.class);
                    String pl8 = snapshot.child("player8").getValue(String.class);
                    String pl9= snapshot.child("player9").getValue(String.class);
                    String pl10 = snapshot.child("player10").getValue(String.class);
                    String pl11 = snapshot.child("player11").getValue(String.class);

                    player1.setText(pl1);
                    player2.setText(pl2);
                    player3.setText(pl3);
                    player4.setText(pl4);
                    player5.setText(pl5);
                    player6.setText(pl6);
                    player7.setText(pl7);
                    player8.setText(pl8);
                    player9.setText(pl9);
                    player10.setText(pl10);
                    player11.setText(pl11);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompoSave data = new CompoSave(player1,player2,player3,player4,player5,player6,player7,player8,player9,player10,player11);
                String uniqueId = "-compo442";
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Compo");
                UserRef.child(uniqueId).setValue(data);
                Toast.makeText(Compo442.this, "Compo save", Toast.LENGTH_SHORT).show();

            }
        });
    }
}