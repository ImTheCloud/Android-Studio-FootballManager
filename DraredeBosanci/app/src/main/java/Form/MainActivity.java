package Form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView apiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the TextView in the layout
        apiResult = findViewById(R.id.apiResult);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api-football-v1.p.rapidapi.com/v3/players/squads?team=541";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the JSON response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("response").getJSONObject(0).getJSONArray("players");

                            // Extract player names
                            ArrayList<String> playerNames = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                String playerName = playerObject.getString("name");
                                playerNames.add(playerName);
                            }

                            // Display player names in the activity
                            apiResult.setText(String.join("\n", playerNames));
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

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
