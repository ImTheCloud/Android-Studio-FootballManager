package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewGame extends AppCompatActivity implements OnMapReadyCallback{

    EditText ETPlayers;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize the FusedLocationProviderClient.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Var player
        ETPlayers = findViewById(R.id.ID_Player);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        Button btnPlayerClaudiu = findViewById(R.id.playerClaudiu);
        Button btnPlayerRuben = findViewById(R.id.playerRuben);
        Button btnPlayerDany = findViewById(R.id.playerDany );
        Button btnPlayerRoberto = findViewById(R.id.playerRoberto );
        Button btnPlayerDenis = findViewById(R.id.playerDenis );
        Button btnPlayerLucian = findViewById(R.id.playerLucian );
        Button btnPlayerDavid = findViewById(R.id.playerDavid );
        Button btnPlayerFlavyus = findViewById(R.id.playerFlavyus );
        Button btnPlayerEdi = findViewById(R.id.playerEdaurd );
        Button btnPlayerYaniv = findViewById(R.id.playerYaniv );
        Button btnPlayerIosif = findViewById(R.id.playerIosif );
        Button btnPlayerSimon = findViewById(R.id.playerSimon );

        EditText etPlayers = findViewById(R.id.ID_Player);

        btnPlayerSimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerSimon.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });
        btnPlayerIosif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerIosif.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });
        btnPlayerYaniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerYaniv.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });
        btnPlayerEdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerEdi.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });
        btnPlayerFlavyus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerFlavyus.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);

            }
        });
        btnPlayerDavid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerDavid.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });

        btnPlayerLucian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerLucian.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });

        btnPlayerDenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerDenis.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });
        btnPlayerRoberto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = btnPlayerRoberto.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? player : currentText + ", " + player;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });
        btnPlayerClaudiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerClaudiu = btnPlayerClaudiu.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? playerClaudiu : currentText + ", " + playerClaudiu;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });

        btnPlayerDany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerDany = btnPlayerDany.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? playerDany : currentText + ", " + playerDany;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });

        btnPlayerRuben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerRuben = btnPlayerRuben.getText().toString();
                String currentText = etPlayers.getText().toString();
                String newText = currentText.isEmpty() ? playerRuben : currentText + ", " + playerRuben;
                etPlayers.setText(newText);
                disableButton(view);
            }
        });


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

    }


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JANUARY";
        if(month == 2)
            return "FEBRUARY";
        if(month == 3)
            return "MARCH";
        if(month == 4)
            return "APRIL";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUNE";
        if(month == 7)
            return "JULY";
        if(month == 8)
            return "AUGUST";
        if(month == 9)
            return "SEPTEMBER";
        if(month == 10)
            return "OCTOBER";
        if(month == 11)
            return "NOVEMBER";
        if(month == 12)
            return "DECEMBER";

        //default should never happen
        return "JAN";
    }

    public void disableButton(View view) {
        Button button = (Button) view;
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }


    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void goToLive(View v){
        Intent i = new Intent(NewGame.this, Live.class);
        i.putExtra("Players", ETPlayers.getText().toString());

//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.whistle_referee);
//        mediaPlayer.start();

        startActivity(i);
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



}
