package Statistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Home.Home;

public class Statistics extends AppCompatActivity {

    private EditText etWin,etTie,etLose,etYellowCard,et5Goal,etRank,etFame;
    private TextView tvGameWrite,tvWinRateWrite,tvapiResult,loading,TVPointsWrite;
    private Button bt_Save,addPlayerButton;
    private Spinner playerPositionSpinner,playerNameSpinner;
    private LinearLayout linearBig;
    private DatabaseReference UserRef;
    private float x1, x2;
    private static final int MIN_DISTANCE = 150;
    private List<String> positionList,playerNames;
    private ArrayAdapter<String> adapterName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        bt_Save = findViewById(R.id.bt_Save);
        tvapiResult = findViewById(R.id.apiResult);
        bt_Save.setVisibility(View.INVISIBLE);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        linearBig =  findViewById(R.id.linearBig);
        linearBig.setVisibility(View.INVISIBLE);
        TVPointsWrite = findViewById(R.id.TVPointsWrite);
        tvGameWrite = findViewById(R.id.TVGameWrite);
        tvWinRateWrite = findViewById(R.id.TVWinRateWrite);
        etFame = findViewById(R.id.ETFame);
        etWin = findViewById(R.id.ETWin);
        etTie = findViewById(R.id.ETTie);
        etLose = findViewById(R.id.ETLose);
        etYellowCard = findViewById(R.id.ETYellowCard);
        et5Goal = findViewById(R.id.ET5Goal);
        etRank = findViewById(R.id.ETRank);
        playerPositionSpinner = findViewById(R.id.playerPositionSpinner);
        playerNameSpinner = findViewById(R.id.playerNameSpinner);
        addPlayerButton = findViewById(R.id.add_player_button);
        bt_Save.setVisibility(View.INVISIBLE);
        addPlayerButton.setVisibility(View.INVISIBLE);

        // Ecrire que des chiffres
        etWin.setInputType(InputType.TYPE_CLASS_NUMBER);
        etTie.setInputType(InputType.TYPE_CLASS_NUMBER);
        etLose.setInputType(InputType.TYPE_CLASS_NUMBER);
        etYellowCard.setInputType(InputType.TYPE_CLASS_NUMBER);
        et5Goal.setInputType(InputType.TYPE_CLASS_NUMBER);
        etRank.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Ajout ecouteur de temps
        etWin.addTextChangedListener(textWatcher);
        etTie.addTextChangedListener(textWatcher);
        etLose.addTextChangedListener(textWatcher);
        etYellowCard.addTextChangedListener(textWatcher);
        et5Goal.addTextChangedListener(textWatcher);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String[] positions = getResources().getStringArray(R.array.positions);
        positionList = Arrays.asList(positions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, positionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerPositionSpinner.setAdapter(adapter);


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail().equals("claudiuppdc7@yahoo.com")) {
            bt_Save.setVisibility(View.VISIBLE);
            addPlayerButton.setVisibility(View.VISIBLE);
        } else {
            etFame.setEnabled(false);
            etWin.setEnabled(false);
            etTie.setEnabled(false);
            etLose.setEnabled(false);
            etYellowCard.setEnabled(false);
            et5Goal.setEnabled(false);
            etRank.setEnabled(false);
        }

        etFame.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    ((EditText) view).setText("");
                }
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference playersRef = database.getReference("Player");

        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> playerNames = new ArrayList<String>();
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String playerName = playerSnapshot.child("name").getValue(String.class);
                    playerNames.add(playerName);
                }
                ArrayAdapter<String> adapterName = new ArrayAdapter<>(Statistics.this, android.R.layout.simple_spinner_item, playerNames);
                playerNameSpinner.setAdapter(adapterName);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Statistics.this);
                builder.setTitle("Add new Player");
                final EditText input = new EditText(Statistics.this);
                builder.setView(input);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Add Player", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPlayerName = input.getText().toString();
                        DatabaseReference newPlayerRef = playersRef.child(newPlayerName);
                        newPlayerRef.child("bonusText").setValue("");
                        newPlayerRef.child("fameText").setValue("");
                        newPlayerRef.child("loseText").setValue("");
                        newPlayerRef.child("name").setValue(newPlayerName);
                        newPlayerRef.child("position").setValue("");
                        newPlayerRef.child("rankText").setValue("");
                        newPlayerRef.child("tieText").setValue("");
                        newPlayerRef.child("winText").setValue("");
                        newPlayerRef.child("yellowText").setValue("");
                    }
                });
                builder.show();
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        playerNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String uniqueId = playerNameSpinner.getSelectedItem().toString();
                retrieveDataFromDatabase(uniqueId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stat_Save data = new Stat_Save(etFame, etWin, etLose, etTie, et5Goal, etYellowCard, etRank, playerPositionSpinner, playerNameSpinner,TVPointsWrite);
                String uniqueId = playerNameSpinner.getSelectedItem().toString(); // get the selected item as a string
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Player");
                UserRef.child(uniqueId).setValue(data); // set the value with the unique ID
                Toast.makeText(Statistics.this, "Player profile save", Toast.LENGTH_SHORT).show();
            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Set up Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Set up API URL
        String teamId = "541";
        String apiUrl = String.format("https://api-football-v1.p.rapidapi.com/v3/players/squads?team=%s", teamId);
        playerPositionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               //   makeApiRequest(queue, apiUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void retrieveDataFromDatabase(String uniqueId) {
        DatabaseReference myRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Player");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Player> players = new ArrayList<>();
                    for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                        String playerName = playerSnapshot.child("name").getValue(String.class);
                        String playerPoints = playerSnapshot.child("point").getValue(String.class);
                        Player player = new Player(playerName, Integer.parseInt(playerPoints));
                        players.add(player);
                    }

                    // trier la liste en fonction des points
                    Collections.sort(players, Collections.reverseOrder());

                    // créer les chaînes de caractères pour les TextViews
                    StringBuilder namesSb = new StringBuilder();
                    StringBuilder pointsSb = new StringBuilder();
                    StringBuilder rankSb = new StringBuilder();
                    int rankCounter = 1;

                    for (Player player : players) {
                        rankSb.append(rankCounter).append("\n");
                        String playerName = player.getName();
                        String playerPoints = String.valueOf(player.getPoints());
                        namesSb.append(playerName).append("\n");
                        pointsSb.append(playerPoints).append("\n");
                        rankCounter++;
                    }

                    TextView name1TextView = findViewById(R.id.name);
                    name1TextView.setText(namesSb.toString());

                    TextView pointsTextView = findViewById(R.id.point);
                    pointsTextView.setText(pointsSb.toString());

                    TextView rank1TextView = findViewById(R.id.Rank);
                    rank1TextView.setText(rankSb.toString());
                } else {
                    // le snapshot est vide, rien à faire
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // traitement de l'erreur d'accès à la base de données
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
        database.getReference("Player").child(uniqueId).addListenerForSingleValueEvent(new ValueEventListener() {
            // votre onDataChange et onCancelled methods ici
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    linearBig.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    String fame = snapshot.child("fameText").getValue(String.class);
                    String win = snapshot.child("winText").getValue(String.class);
                    String tie = snapshot.child("tieText").getValue(String.class);
                    String lose = snapshot.child("loseText").getValue(String.class);
                    String yellowCard = snapshot.child("yellowText").getValue(String.class);
                    String fiveGoal = snapshot.child("bonusText").getValue(String.class);
                    String rank = snapshot.child("rankText").getValue(String.class);
                    String position = snapshot.child("position").getValue(String.class);
                    int positionIndex = positionList.indexOf(position);

                    playerPositionSpinner.setSelection(positionIndex);
                    etFame.setText(fame);
                    etWin.setText(win);
                    etTie.setText(tie);
                    etLose.setText(lose);
                    etYellowCard.setText(yellowCard);
                    et5Goal.setText(fiveGoal);
                    etRank.setText(rank);
                } else {
                    loading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // traitement de l'erreur d'accès à la base de données
            }
        });
    }
    private String getRandomPlayer(List<String> players) {
        int randomIndex = new Random().nextInt(players.size());
        return players.get(randomIndex);
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Mettre à jour le calcul lorsque le texte est modifié
            int valueWin = getValue(etWin.getText().toString());
            int valueTie = getValue(etTie.getText().toString());
            int valueLose = getValue(etLose.getText().toString());
            int valueYellowCard = getValue(etYellowCard.getText().toString());
            int bonus5Goal = getValue(et5Goal.getText().toString());
            int totalGames = valueWin + valueTie + valueLose;
            double winRate = totalGames > 0 ? ((double) valueWin / totalGames) * 100 : 0.0;
            int points = ((valueWin * 3) + valueTie + bonus5Goal) - (valueYellowCard / 3);
            TVPointsWrite.setText(String.valueOf(points));
            tvGameWrite.setText(String.valueOf(totalGames));
            tvWinRateWrite.setText(String.format("%.0f%%", winRate));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private int getValue(String text) {
        if (text.isEmpty()) {
            return 0; // Valeur par défaut si le champ est vide
        } else {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return 0; // Valeur par défaut si le champ ne contient pas de nombre
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void makeApiRequest(RequestQueue queue, String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the JSON response
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("response").getJSONObject(0).getJSONArray("players");

                            // Filter players by selected position
                            List<String> playerNames = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                String playerPosition = playerObject.getString("position");
                                if (playerPosition.equalsIgnoreCase(playerPositionSpinner.getSelectedItem().toString())) {
                                    String playerName = playerObject.getString("name");
                                    playerNames.add(playerName);
                                }
                            }

                            // Display result
                            if (playerNames.size() > 0) {
                                String randomPlayerName = getRandomPlayer(playerNames);
                                tvapiResult.setText(String.format("Like : %s", randomPlayerName));
                            } else {
                                tvapiResult.setText(String.format("No players found with position: %s", playerPositionSpinner.getSelectedItem().toString()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvapiResult.setText("That didn't work!");
            }
        }) {
            // Add headers to the request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("X-RapidAPI-Key", "2e8e0f243bmshb40a5716fd99fb3p16e3e1jsn6b6864f3d84e");
                headers.put("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com");
                return headers;
            }
        };

        // Add request to queue
        queue.add(stringRequest);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (x2 > x1) {
                        int currentPosition = playerNameSpinner.getSelectedItemPosition();
                        if (currentPosition > 0) {
                            playerNameSpinner.setSelection(currentPosition - 1);
                        }
                    } else {
                        int currentPosition = playerNameSpinner.getSelectedItemPosition();
                        if (currentPosition < playerNameSpinner.getAdapter().getCount() - 1) {
                            playerNameSpinner.setSelection(currentPosition + 1);
                        }

                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    public void goToLeft(View v){
        int currentPosition = playerNameSpinner.getSelectedItemPosition();
        if (currentPosition > 0) {
            playerNameSpinner.setSelection(currentPosition - 1);
        }
    }

    public void goToRight(View v){
        int currentPosition = playerNameSpinner.getSelectedItemPosition();
        if (currentPosition < playerNameSpinner.getAdapter().getCount() - 1) {
            playerNameSpinner.setSelection(currentPosition + 1);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Statistics.this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_innn, R.anim.fade_out);
    }

}
