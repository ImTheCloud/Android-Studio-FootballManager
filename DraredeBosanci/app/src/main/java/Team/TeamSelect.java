package Team;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.draredebosanci.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Home.NewGame;
import Home.Home;
import Live.LiveSelected;

public class TeamSelect extends AppCompatActivity{
    private EditText etPlayers1,etPlayers2;
    public static EditText  ttimerFirst,ttimerHalfTime,ttimerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_select);


        ttimerFirst = findViewById(R.id.ID_Timer_first);
        ttimerHalfTime = findViewById(R.id.ID_Timer_halftime);
        ttimerSecond = findViewById(R.id.ID_Timer_second);

        ttimerFirst.setInputType(InputType.TYPE_CLASS_NUMBER);
        ttimerHalfTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        ttimerSecond.setInputType(InputType.TYPE_CLASS_NUMBER);

        etPlayers1 = findViewById(R.id.ID_Team1);
        etPlayers2 = findViewById(R.id.ID_Team2);

        ListView playerListView = findViewById(R.id.playerListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("Loading...");
        playerListView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference playersRef = database.getReference("Player");

        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                if (dataSnapshot.exists()) {
                    ArrayList<String> playerNames = new ArrayList<String>();
                    for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                        String playerName = playerSnapshot.child("name").getValue(String.class);
                        playerNames.add(playerName);
                    }
                    adapter.addAll(playerNames);
                }else{
                    adapter.add("No players in statistics");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                EditText selectedEditText = null;
                if (etPlayers1.isFocused()) {
                    selectedEditText = etPlayers1;
                } else if (etPlayers2.isFocused()) {
                    selectedEditText = etPlayers2;
                }
                if (selectedEditText != null) {
                    String currentText = selectedEditText.getText().toString();
                    String newText = currentText.isEmpty() ? selectedItem : currentText + ", " + selectedItem;
                    selectedEditText.setText(newText);
                }
            }
        });



// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(TeamSelect.this, NewGame.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void goToHouse(View v){
        startActivity(new Intent(TeamSelect.this, Home.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    public void goToLiveSelected(View v){
        String timerFirstString = ttimerFirst.getText().toString();
        String timerHalfTimeString = ttimerHalfTime.getText().toString();
        String timerSecondString = ttimerSecond.getText().toString();

        if(timerHalfTimeString.equals("") || timerFirstString.equals("") || timerSecondString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please insert time in each field", Toast.LENGTH_SHORT).show();
            return;
        }

        String team1String1 = etPlayers1.getText().toString();
        String team1String2 = etPlayers2.getText().toString();
        if(team1String1.equals("") || team1String2.equals("") ) {
            Toast.makeText(getApplicationContext(), "Please insert at least one name in each field", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(TeamSelect.this, LiveSelected.class);

        String players = etPlayers1.getText().toString();
        i.putExtra("players_data", players);
        String players2 = etPlayers2.getText().toString();
        i.putExtra("players_data2", players2);

        i.putExtra("timerFirst", ttimerFirst.getText().toString());
        i.putExtra("timerHalf", ttimerHalfTime.getText().toString());
        i.putExtra("timerSecond", ttimerSecond.getText().toString());

        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
