package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RandomTeam extends AppCompatActivity {

    private EditText etPlayers;
    private LinearLayout linearLayoutBosanci1,linearLayoutBosanci2,linearLayoutHelb1,linearLayoutHelb2;
    private Button playerDareDeBosanci,playerHelb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_team);

         linearLayoutBosanci1 = findViewById(R.id.linearLayoutBosanci1);
         linearLayoutBosanci2 = findViewById(R.id.linearLayoutBosanci2);
         linearLayoutHelb1 = findViewById(R.id.linearLayoutHelb1);
         linearLayoutHelb2 = findViewById(R.id.linearLayoutHelb2);
         playerDareDeBosanci = findViewById(R.id.bt_drareDeBosanci);
         playerHelb = findViewById(R.id.bt_Helb);
         etPlayers = findViewById(R.id.ID_Player);
         Button[] buttons = new Button[] {
                findViewById(R.id.playerClaudiu),
                findViewById(R.id.playerRuben),
                findViewById(R.id.playerDany),
                findViewById(R.id.playerRoberto),
                findViewById(R.id.playerDenis),
                findViewById(R.id.playerLucian),
                findViewById(R.id.playerDavid),
                findViewById(R.id.playerFlavyus),
                findViewById(R.id.playerSimon),
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void disableButton(View view) {
        Button button = (Button) view;
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }

    public void goToLive(View v){
        Intent i = new Intent(RandomTeam.this, LiveRandom.class);
        i.putExtra("Players", etPlayers.getText().toString());
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.whistle_referee);
//        mediaPlayer.start();
        startActivity(i);
    }
}
