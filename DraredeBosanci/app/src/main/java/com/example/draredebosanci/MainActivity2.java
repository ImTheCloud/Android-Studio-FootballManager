package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

    private TextView positionTextView;
    private TextView playerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        positionTextView = findViewById(R.id.positon);
        playerTextView = findViewById(R.id.text);

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
                            String position = positionTextView.getText().toString();
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
                                String playerInfo = "Name: " + firstName + " " + lastName;
                                playerTextView.setText(playerInfo);
                            } else {
                                playerTextView.setText("No players found with the specified position.");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            playerTextView.setText("Error parsing JSON response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                playerTextView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
