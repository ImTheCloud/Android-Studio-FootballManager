package com.example.draredebosanci;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private Spinner playerPositionSpinner;
    private TextView apiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playerPositionSpinner = findViewById(R.id.playerPositionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.positions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerPositionSpinner.setAdapter(adapter);

        apiResult = findViewById(R.id.apiResult);

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
