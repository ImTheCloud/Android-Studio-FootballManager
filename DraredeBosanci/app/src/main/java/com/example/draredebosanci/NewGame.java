package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewGame extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private   EditText etPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        LinearLayout linearLayoutBosanci1 = findViewById(R.id.linearLayoutBosanci1);
        LinearLayout linearLayoutBosanci2 = findViewById(R.id.linearLayoutBosanci2);
        LinearLayout linearLayoutHelb1 = findViewById(R.id.linearLayoutHelb1);
        LinearLayout linearLayoutHelb2 = findViewById(R.id.linearLayoutHelb2);
        Button  playerDareDeBosanci = findViewById(R.id.bt_drareDeBosanci);
        Button  playerHelb = findViewById(R.id.bt_Helb);
        playerDareDeBosanci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutBosanci1.getVisibility() == View.VISIBLE && linearLayoutBosanci2.getVisibility() == View.VISIBLE) {
                    linearLayoutBosanci1.setVisibility(View.INVISIBLE);
                    linearLayoutBosanci2.setVisibility(View.INVISIBLE);
                } else {
                    linearLayoutBosanci1.setVisibility(View.VISIBLE);
                    linearLayoutBosanci2.setVisibility(View.VISIBLE);
                }
            }
        });

        playerHelb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutHelb1.getVisibility() == View.VISIBLE && linearLayoutHelb2.getVisibility() == View.VISIBLE) {
                    linearLayoutHelb1.setVisibility(View.INVISIBLE);
                    linearLayoutHelb2.setVisibility(View.INVISIBLE);
                } else {
                    linearLayoutHelb1.setVisibility(View.VISIBLE);
                    linearLayoutHelb2.setVisibility(View.VISIBLE);
                }
            }
        });

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button[] buttons = new Button[] {
                findViewById(R.id.playerClaudiu),
                findViewById(R.id.playerRuben),
                findViewById(R.id.playerDany),
                findViewById(R.id.playerRoberto),
                findViewById(R.id.playerDenis),
                findViewById(R.id.playerLucian),
                findViewById(R.id.playerDavid),
                findViewById(R.id.playerFlavyus),
                findViewById(R.id.playerEdaurd),
                findViewById(R.id.playerYaniv),
                findViewById(R.id.playerIosif),
                findViewById(R.id.playerBartek),
                findViewById(R.id.playerMichel),
                findViewById(R.id.playerBasile),
                findViewById(R.id.playerClaudiu2),
                findViewById(R.id.playerMohamed),
                findViewById(R.id.playerRedouan),
                findViewById(R.id.playerYassin),
                findViewById(R.id.playerArthur),
                findViewById(R.id.playerDamien),
                findViewById(R.id.playerAnthony),
                findViewById(R.id.playerLeo),

        };

        etPlayers = findViewById(R.id.ID_Player);

        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String player = button.getText().toString();
                    String currentText = etPlayers.getText().toString();
                    String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                    etPlayers.setText(newText);
                    disableButton(view);
                }
            });
        }
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize the FusedLocationProviderClient.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }




    public void disableButton(View view) {
        Button button = (Button) view;
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check if we have permission to access the user's location.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission from the user to access their location.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // If we have permission, get the user's last known location and move the camera there.
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(userLocation)
                            .title("Marker at User Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f));
                }
            });
        }
    }

    // Called when the user responds to the permission request dialog.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If the user grants permission, get the user's last known location and move the camera there.
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions()
                                .position(userLocation)
                                .title("Marker at User Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f));
                    }
                });
            }
        }
    }
    public void goToLive(View v){
        Intent i = new Intent(NewGame.this, Live.class);
        i.putExtra("Players", etPlayers.getText().toString());
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.whistle_referee);
//        mediaPlayer.start();
        startActivity(i);
    }

}
