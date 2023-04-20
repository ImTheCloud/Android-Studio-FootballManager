package Home;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import Team.TeamRandom;
import Team.TeamSelect;

public class NewGame extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText locationEditText;
    private TextView dateTextView,temperatureTextView;
    private Button fetchButton;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    public static LatLng userLocation,ourField;
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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
        database.getReference("Game").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null && snapshot.getValue() != null) {
                    for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
                        Map<String, Object> gameData = (Map<String, Object>) gameSnapshot.getValue();
                        Map<String, Object> userLocationData = (Map<String, Object>) gameData.get("userLocation");
                        String latitude = userLocationData.get("latitude").toString();
                        String longitude = userLocationData.get("longitude").toString();
                        // Ajouter un marqueur à la carte pour cette localisation
                        LatLng location = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title("Game played here")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_marker)));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    ourField = new LatLng(50.827511 , 4.297444);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ourField, 15f));
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng ourField = new LatLng(50.827511 , 4.297444);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ourField, 15f));
                    }
                });
            }
        }
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