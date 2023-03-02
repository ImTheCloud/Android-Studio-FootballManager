package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

public class Home extends AppCompatActivity {
    private boolean isSoundEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button soundButton = findViewById(R.id.soundOff);
        final Drawable soundOnImage = getResources().getDrawable(R.drawable.sound_off);
        final Drawable soundOffImage = getResources().getDrawable(R.drawable.sound_on);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSoundEnabled) {
                    // Désactiver le son
                    isSoundEnabled = false;
                    Intent intent = new Intent(Home.this, MyMusicService.class);
                    startService(intent);
                    soundButton.setCompoundDrawablesWithIntrinsicBounds(soundOffImage, null, null, null);
                } else {
                    // Réactiver le son
                    isSoundEnabled = true;
                    Intent intent = new Intent(Home.this, MyMusicService.class);
                    stopService(intent);
                    soundButton.setCompoundDrawablesWithIntrinsicBounds(soundOnImage, null, null, null);
                }
            }
        });


    }


    public void goToLive(View v){
        startActivity(new Intent(Home.this, OldGame.class));
    }
    public void goToCompo(View v){
        startActivity(new Intent(Home.this, CompoChoice.class));
    }

    public void goToContact(View v){
        startActivity(new Intent(Home.this, Contact.class));
    }

    public void goToNewGame(View v){
        startActivity(new Intent(Home.this, NewGame.class));
    }

    public void goToOldGame(View v){
        startActivity(new Intent(Home.this, OldGame.class));
    }

    public void goToRanking(View v){
        startActivity(new Intent(Home.this, Form.class));
    }

//    public void goToMap(View v){
//        startActivity(new Intent(Home.this, MapsActivity.class));
//    }





}