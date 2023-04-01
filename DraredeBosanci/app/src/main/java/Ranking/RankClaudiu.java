package Ranking;

import static Team.NewGame.date;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Firebase.Form;
import Firebase.Game;


public class RankClaudiu extends AppCompatActivity {

    private EditText etWin,etTie,etLose,etYellowCard,et5Goal,etRank,etFame;
    private TextView tvPointsWrite,tvGameWrite,tvWinRateWrite,apiResult;
    private Button bt_Save;
    private Context context;
    DatabaseReference UserRef;
    private Spinner playerPositionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank_claudiu);

        bt_Save = findViewById(R.id.bt_Save);
        context = this;
        etFame = findViewById(R.id.ETFameClaudiu);
        etWin = findViewById(R.id.ETWinClaudiu);
        etTie = findViewById(R.id.ETTieClaudiu);
        etLose = findViewById(R.id.ETLoseClaudiu);
        etYellowCard = findViewById(R.id.ETYellowCardClaudiu);
        et5Goal = findViewById(R.id.ET5GoalClaudiu);
        etRank = findViewById(R.id.ETRankClaudiu);
        apiResult = findViewById(R.id.apiResult);
        playerPositionSpinner = findViewById(R.id.playerPositionSpinner);
        tvPointsWrite = findViewById(R.id.TVPointsWriteClaudiu);
        tvGameWrite = findViewById(R.id.TVGameWriteClaudiu);
        tvWinRateWrite = findViewById(R.id.TVWinRateWriteClaudiu);

        // Ajouter les écouteurs d'événements de modification de texte pour chaque EditText
        etWin.addTextChangedListener(textWatcher);
        etTie.addTextChangedListener(textWatcher);
        etLose.addTextChangedListener(textWatcher);
        etYellowCard.addTextChangedListener(textWatcher);
        et5Goal.addTextChangedListener(textWatcher);

        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Form data = new Form(etFame,etWin,etLose,etLose,et5Goal,etYellowCard,etRank);
                UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Player/Claudiu");
                UserRef.push().child("fame").setValue(data.getFameText());
                Toast.makeText(RankClaudiu.this, "Game save", Toast.LENGTH_SHORT).show();

            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        playerPositionSpinner = findViewById(R.id.playerPositionSpinner);
        apiResult = findViewById(R.id.apiResult);

        // Set up spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.positions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerPositionSpinner.setAdapter(adapter);

        // Set up Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Set up API URL
        String teamId = "541";
        String apiUrl = String.format("https://api-football-v1.p.rapidapi.com/v3/players/squads?team=%s", teamId);

        // Set up listener for spinner item selection
        playerPositionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeApiRequest(queue, apiUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
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
            // Ne rien faire avant la modification du texte
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
            // Ne rien faire après la modification du texte
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
                                apiResult.setText(String.format("Like : %s", randomPlayerName));
                            } else {
                                apiResult.setText(String.format("No players found with position: %s", playerPositionSpinner.getSelectedItem().toString()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResult.setText("That didn't work!");
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
}
