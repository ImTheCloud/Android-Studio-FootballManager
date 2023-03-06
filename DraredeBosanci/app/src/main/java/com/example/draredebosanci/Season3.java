package com.example.draredebosanci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Season3 extends AppCompatActivity {
    private boolean isSoundEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season3);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = null;
        if (user != null) {
            userEmail = user.getEmail();
        }
        Button newGameButton = findViewById(R.id.BT_New_Game);
        if (!userEmail.equals("claudiuppdc7@yahoo.com")) {
            newGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Only the referee can create a new game", Toast.LENGTH_SHORT).show();
                }
            });
        }


        final Button soundButton = findViewById(R.id.soundOff);
        final Drawable soundOnImage = getResources().getDrawable(R.drawable.sound_off);
        final Drawable soundOffImage = getResources().getDrawable(R.drawable.sound_on);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSoundEnabled) {
                    // Désactiver le son
                    isSoundEnabled = false;
                    Intent intent = new Intent(Season3.this, MyMusicService.class);
                    startService(intent);
                    soundButton.setCompoundDrawablesWithIntrinsicBounds(soundOffImage, null, null, null);
                } else {
                    // Réactiver le son
                    isSoundEnabled = true;
                    Intent intent = new Intent(Season3.this, MyMusicService.class);
                    stopService(intent);
                    soundButton.setCompoundDrawablesWithIntrinsicBounds(soundOnImage, null, null, null);
                }
            }
        });


    }
// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void goToLive(View v){
        startActivity(new Intent(Season3.this, OldGame.class));
    }
    public void goToCompo(View v){
        startActivity(new Intent(Season3.this, CompoChoice.class));
    }

    public void goToContact(View v){
        startActivity(new Intent(Season3.this, Contact.class));
    }

    public void goToTeamSelection(View v){
        startActivity(new Intent(Season3.this, TeamSelection.class));
    }

    public void goToOldGame(View v){
        startActivity(new Intent(Season3.this, OldGame.class));
    }

    public void goToRanking(View v){
        startActivity(new Intent(Season3.this, Form.class));
    }






}