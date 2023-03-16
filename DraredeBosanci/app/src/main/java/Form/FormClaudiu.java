package Form;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.draredebosanci.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class FormClaudiu extends AppCompatActivity {

    private EditText etWin,etTie,etLose,etYellowCard,et5Goal;
    private TextView tvPointsWrite,tvGameWrite,tvWinRateWrite,apiResult;

    private Spinner playerPositionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_claudiu);

        etWin = findViewById(R.id.ETWinClaudiu);
        etTie = findViewById(R.id.ETTieClaudiu);
        etLose = findViewById(R.id.ETLoseClaudiu);
        etYellowCard = findViewById(R.id.ETYellowCardClaudiu);
        et5Goal = findViewById(R.id.ET5GoalClaudiu);
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // api
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.positions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerPositionSpinner.setAdapter(adapter);

        // Ajouter un TextWatcher pour détecter les changements de texte
        playerPositionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPosition = parent.getItemAtPosition(position).toString();
                searchPlayersByPosition(selectedPosition);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                apiResult.setText("Choose a position");
            }
        });

    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void searchPlayersByPosition(String position) {
        String url = "https://api-football-v1.p.rapidapi.com/v3/players/squads?team=541";
        String apiKey = "2e8e0f243bmshb40a5716fd99fb3p16e3e1jsn6b6864f3d84e";
        String apiHost = "api-football-v1.p.rapidapi.com";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray playersArray = response.getJSONObject("response").getJSONArray("players");
                            ArrayList<JSONObject> matchingPlayers = new ArrayList<>();
                            for (int i = 0; i < playersArray.length(); i++) {
                                JSONObject player = playersArray.getJSONObject(i);
                                if (player.getString("position").equals(position)) {
                                    matchingPlayers.add(player);
                                }
                            }
                            if (matchingPlayers.size() > 0) {
                                // Choose a random player from the list of matching players
                                Random random = new Random();
                                int randomIndex = random.nextInt(matchingPlayers.size());
                                JSONObject randomPlayer = matchingPlayers.get(randomIndex);
                                String firstName = randomPlayer.getString("firstname");
                                String lastName = randomPlayer.getString("lastname");
                                String playerInfo = "Like: " + firstName + " " + lastName;
                                apiResult.setText(playerInfo);
                            } else {
                                apiResult.setText("No players");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            apiResult.setText("Error parsing JSON response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResult.setText("That didn't work!");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", apiKey);
                headers.put("X-RapidAPI-Host", apiHost);
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
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



}
