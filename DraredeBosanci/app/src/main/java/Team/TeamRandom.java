package Team;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.draredebosanci.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Home.Home;
import Live.LiveRandom;
import Home.NewGame;
import Statistics.StatSpinner;
import Statistics.Statistics;

public class TeamRandom extends AppCompatActivity {
    private EditText etPlayers;
    public static EditText  timerFirst,timerHalfTime,timerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_random);

        etPlayers = findViewById(R.id.ID_Player);
        timerHalfTime = findViewById(R.id.ID_Timer_halftime);
        timerFirst = findViewById(R.id.ID_Timer_first);
        timerSecond = findViewById(R.id.ID_Timer_second);


        timerHalfTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        timerFirst.setInputType(InputType.TYPE_CLASS_NUMBER);
        timerSecond.setInputType(InputType.TYPE_CLASS_NUMBER);

        timerFirst.setInputType(InputType.TYPE_CLASS_NUMBER);
        timerFirst.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        timerFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    timerFirst.setText(s.subSequence(0, 2));
                    timerFirst.setSelection(2);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        timerHalfTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        timerHalfTime.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        timerHalfTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    timerHalfTime.setText(s.subSequence(0, 2));
                    timerHalfTime.setSelection(2);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        timerSecond.setInputType(InputType.TYPE_CLASS_NUMBER);
        timerSecond.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        timerSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    timerSecond.setText(s.subSequence(0, 2));
                    timerSecond.setSelection(2);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        ListView playerListView = findViewById(R.id.playerListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        playerListView.setAdapter(adapter);
        adapter.add("Loading...");

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
                String currentText = etPlayers.getText().toString();
                String[] playerNames = currentText.split(", "); // split the names using comma and space as delimiter
                boolean isPlayerAlreadyAdded = false;
                for (String playerName : playerNames) {
                    if (playerName.equals(selectedItem)) {
                        isPlayerAlreadyAdded = true; // check if player name is already added
                        break;
                    }
                }
                if (!isPlayerAlreadyAdded) {
                    String newText = currentText.isEmpty() ? selectedItem : currentText + ", " + selectedItem;
                    etPlayers.setText(newText);
                    view.setClickable(false); // set clicked item to be unclickable
                    adapter.remove(selectedItem); // remove clicked item from ListView
                }
            }
        });



    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void disableButton(View view) {
        Button button = (Button) view;
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(TeamRandom.this, NewGame.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void goToHouse(View v){
        startActivity(new Intent(TeamRandom.this, Home.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void goToLive(View v) {
        String timerHalfTimeString = timerHalfTime.getText().toString();
        String timerFirstString = timerFirst.getText().toString();
        String timerSecondString = timerSecond.getText().toString();

        if(timerHalfTimeString.equals("") || timerFirstString.equals("") || timerSecondString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please insert time in each field", Toast.LENGTH_SHORT).show();
            return;
        }

        String team1String = etPlayers.getText().toString();
        String[] players = team1String.split(",");

        if(players.length < 2) {
            Toast.makeText(getApplicationContext(), "Please insert at least two player names", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(TeamRandom.this, LiveRandom.class);
        i.putExtra("Players", etPlayers.getText().toString());
        i.putExtra("timerFirst", timerFirstString);
        i.putExtra("timerHalf", timerHalfTimeString);
        i.putExtra("timerSecond", timerSecondString);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
