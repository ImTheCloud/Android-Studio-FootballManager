package Ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.IOError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Firebase.Form;

public class RankIosif extends AppCompatActivity {

    private EditText etWin,etTie,etLose,etYellowCard,et5Goal,etRank,etFame;
    private TextView tvPointsWrite,tvGameWrite,tvWinRateWrite,tvapiResult;
    private Button bt_Save;
    private Spinner playerPositionSpinner;
    private LinearLayout linearBig;
    DatabaseReference UserRef;
    private float x1, x2;
    private static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank_iosif);

        bt_Save = findViewById(R.id.bt_Save);
        tvapiResult = findViewById(R.id.apiResult);
        bt_Save.setVisibility(View.INVISIBLE);

        TextView loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        linearBig =  findViewById(R.id.linearBig);
        linearBig.setVisibility(View.INVISIBLE);

        playerPositionSpinner = findViewById(R.id.playerPositionSpinner);
        tvPointsWrite = findViewById(R.id.TVPointsWrite);
        tvGameWrite = findViewById(R.id.TVGameWrite);
        tvWinRateWrite = findViewById(R.id.TVWinRateWrite);

        etFame = findViewById(R.id.ETFame);
        etWin = findViewById(R.id.ETWin);
        etTie = findViewById(R.id.ETTie);
        etLose = findViewById(R.id.ETLose);
        etYellowCard = findViewById(R.id.ETYellowCard);
        et5Goal = findViewById(R.id.ET5Goal);
        etRank = findViewById(R.id.ETRank);

        String[] positions = getResources().getStringArray(R.array.positions);
        List<String> positionList = Arrays.asList(positions);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, positionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerPositionSpinner.setAdapter(adapter);

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

        // Vérifiez si l'utilisateur est connecté avec l'adresse e-mail spécifiée
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail().equals("claudiuppdc7@yahoo.com")) {
        } else {
            etFame.setEnabled(false);
            etWin.setEnabled(false);
            etTie.setEnabled(false);
            etLose.setEnabled(false);
            etYellowCard.setEnabled(false);
            et5Goal.setEnabled(false);
            etRank.setEnabled(false);

        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
        database.getReference("Player").child("Iosif").child("-dataForIosif").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    bt_Save.setVisibility(View.VISIBLE);

                }
                else{
                    bt_Save.setVisibility(View.VISIBLE);

                    loading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals("claudiuppdc7@yahoo.com", FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    Toast.makeText(RankIosif.this, "Only the referee can save the profil", Toast.LENGTH_SHORT).show();
                } else {
                Form data = new Form(etFame,etWin,etLose,etTie,et5Goal,etYellowCard,etRank,playerPositionSpinner);
                String uniqueId = "-dataForIosif"; // use the same unique id
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Player/Iosif");
                UserRef.child(uniqueId).setValue(data); // set value with unique id
                Toast.makeText(RankIosif.this, "Player profile save", Toast.LENGTH_SHORT).show();
                }
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // Set up Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Set up API URL
        String teamId = "541";
        String apiUrl = String.format("https://api-football-v1.p.rapidapi.com/v3/players/squads?team=%s", teamId);

        // Set up listener for spinner item selection
        playerPositionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // makeApiRequest(queue, apiUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            tvPointsWrite.setText(String.valueOf(points));
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(RankIosif.this, Rank.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_innn, R.anim.fade_out);
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
                        Intent intent = new Intent(RankIosif.this, RankEduard.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    } else {
                        Intent intent = new Intent(RankIosif.this, RankDany.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    public void goToLeft(View v){
        Intent intent = new Intent(RankIosif.this, RankEduard.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void goToRight(View v){
        Intent intent = new Intent(RankIosif.this, RankDany.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
