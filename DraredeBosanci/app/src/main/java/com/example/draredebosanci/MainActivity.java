package com.example.draredebosanci;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    private Button fetchButton;
    private TextView temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationEditText = findViewById(R.id.location_edittext);
        fetchButton = findViewById(R.id.fetch_button);
        temperatureTextView = findViewById(R.id.temperature_textview);

        fetchButton.setOnClickListener(v -> {
            String location = locationEditText.getText().toString();
            if (!location.isEmpty()) {
                fetchTemperature(location);
            } else {
                Toast.makeText(MainActivity.this, "Veuillez entrer un lieu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTemperature(String location) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=fb665f2037e41531ac80292d5a31dc2c";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main = response.getJSONObject("main");
                            double temperature = main.getDouble("temp") - 273.15;
                            temperatureTextView.setText(String.format("%.1f°C", temperature));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Erreur lors de la récupération de la température", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Erreur lors de la récupération de la température", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

