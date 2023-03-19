package com.example.draredebosanci;

import android.os.Bundle;
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

    private TextView locationTextView;
    private TextView temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTextView = findViewById(R.id.location_textview);
        temperatureTextView = findViewById(R.id.temperature_textview);


        fetchTemperature();
    }

    private void fetchTemperature() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + getLocation() + "&appid=fb665f2037e41531ac80292d5a31dc2c";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main = response.getJSONObject("main");
                            int temperature = (int) (main.getDouble("temp") - 273.15);
                            temperatureTextView.setText(String.format("Temperature Anderlecht : "+String.valueOf(temperature))+ "°C");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error while fetching temperature", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error while fetching temperature", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private String getLocation() {
        // Here you can add code to retrieve the user's location
        // For the purpose of this example, we will return a hardcoded location
        return "Anderlecht";
    }
}
