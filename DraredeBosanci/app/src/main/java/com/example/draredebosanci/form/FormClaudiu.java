package com.example.draredebosanci.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.draredebosanci.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    private void searchPlayersByPosition(String position) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://gist.githubusercontent.com/jamesmwali/11b34a6d4c87644915573e54fbd34ac5/raw/93124e101231f8cbf14ff48ca191156059d6c41f/playerlist.json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<JSONObject> matchingPlayers = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject player = jsonArray.getJSONObject(i);
                                if (player.getString("position").equals(position)) {
                                    matchingPlayers.add(player);
                                }
                            }
                            if (matchingPlayers.size() > 0) {
                                // Choose a random player from the list of matching players
                                Random random = new Random();
                                int randomIndex = random.nextInt(matchingPlayers.size());
                                JSONObject randomPlayer = matchingPlayers.get(randomIndex);
                                String firstName = randomPlayer.getString("first_name");
                                String lastName = randomPlayer.getString("last_name");
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
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
