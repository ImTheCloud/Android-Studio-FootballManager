package Home;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.draredebosanci.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Meteo extends AppCompatActivity {
    private EditText locationEditText;
    private TextView temperatureTextView;
    private Button fetchButton;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meteo);
        locationEditText = findViewById(R.id.city);
        fetchButton = findViewById(R.id.searchMeteo);
        temperatureTextView = findViewById(R.id.temperature_textview);
        image = (ImageView) findViewById(R.id.imageView);

        fetchButton.setOnClickListener(v -> {
            String location = locationEditText.getText().toString();
            if (!location.isEmpty()) {
                fetchTemperature(location);
            } else {
                Toast.makeText(this, "Enter a city please", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTemperature(String location) {
        if (location.isEmpty()) {
            Toast.makeText(this, "Enter a city please", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=fb665f2037e41531ac80292d5a31dc2c";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        int temperature = (int) Math.round(main.getDouble("temp") - 273.15);
                        temperatureTextView.setText(temperature + "°C");

                        // Add the code to display an image based on temperature
                        if (temperature < 15) {
                            image.setImageResource(R.drawable.cloud);
                        } else {
                            image.setImageResource(R.drawable.sun);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "City invalid", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "City invalid", Toast.LENGTH_SHORT).show();
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
