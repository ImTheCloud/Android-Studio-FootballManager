package com.example.draredebosanci;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.drawerlayout.widget.DrawerLayout;


public class Season3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isSoundEnabled = true;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season3);




        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home);
        }



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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


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