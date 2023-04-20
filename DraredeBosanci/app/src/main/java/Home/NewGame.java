package Home;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.draredebosanci.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import Team.TeamRandom;
import Team.TeamSelect;

public class NewGame extends AppCompatActivity {
    private EditText locationEditText;
    private TextView dateTextView,temperatureTextView;
    private Button fetchButton;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private float x1, x2;
    private static final int MIN_DISTANCE = 150;

    public static String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_new_game);

        locationEditText = findViewById(R.id.city);
        fetchButton = findViewById(R.id.BT_meteo);
        temperatureTextView = findViewById(R.id.temperature_textview);
        dateTextView = findViewById(R.id.date_textview);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateTextView.setText(date);


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        fetchTemperature("Anderlecht");
        fetchButton.setOnClickListener(v -> {
            String location = locationEditText.getText().toString();
            if (!location.isEmpty()) {
                fetchTemperature(location);

            } else {
                Toast.makeText(this, "Enter a location", Toast.LENGTH_SHORT).show();
            }
        });

        // On create end
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void fetchTemperature(String location) {
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Invalid location", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
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
                        Intent intent = new Intent(this, TeamSelect.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


                    } else {
                        Intent intent = new Intent(this, TeamRandom.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(NewGame.this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    public void goToRandomTeam(View v){
        startActivity(new Intent(NewGame.this, TeamRandom.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
    public void goToSelectedTeam(View v){
        startActivity(new Intent(NewGame.this, TeamSelect.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

}