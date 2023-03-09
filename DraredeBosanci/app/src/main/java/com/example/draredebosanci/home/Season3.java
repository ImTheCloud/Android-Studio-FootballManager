package com.example.draredebosanci.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.draredebosanci.MyMusicService;
import com.example.draredebosanci.profile.DBHelper;
import com.example.draredebosanci.R;
import com.example.draredebosanci.profile.UploadActivity;
import com.example.draredebosanci.compo.CompoChoice;
import com.example.draredebosanci.firebase.LoginActivity;
import com.example.draredebosanci.form.Form;
import com.example.draredebosanci.team.TeamSelection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.drawerlayout.widget.DrawerLayout;


public class Season3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isSoundEnabled = true;
    private DrawerLayout drawerLayout;
    FloatingActionButton compo;
    private Button newGameButton,oldGame,liveGame,ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season3);


        oldGame = findViewById(R.id.BT_Old_Game);
        liveGame = findViewById(R.id.BT_Live_Game);
        ranking = findViewById(R.id.BT_Ranking);


        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        compo = findViewById(R.id.BT_Compo);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        View header = navigationView.getHeaderView(0);
        ImageView navImage = (ImageView) header.findViewById(R.id.navImage);
        TextView navName = (TextView) header.findViewById(R.id.navName);
        TextView navEmail = (TextView) header.findViewById(R.id.navEmail);

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getUser();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Profile Details", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                navName.setText(""+cursor.getString(0));
                navEmail.setText(""+cursor.getString(1));
                byte[] imageByte = cursor.getBlob(2);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                navImage.setImageBitmap(bitmap);
            }
        }

        compo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Season3.this, CompoChoice.class);
                startActivity(intent);
            }
        });



        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                newGameButton.setVisibility(View.INVISIBLE);
                newGameButton.setEnabled(false);
                oldGame.setEnabled(false);
                oldGame.setVisibility(View.INVISIBLE);
                liveGame.setEnabled(false);
                liveGame.setVisibility(View.INVISIBLE);
                ranking.setEnabled(false);
                ranking.setVisibility(View.INVISIBLE);
                compo.setEnabled(false);
                compo.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                oldGame.setEnabled(true);
                oldGame.setVisibility(View.VISIBLE);
                liveGame.setEnabled(true);
                liveGame.setVisibility(View.VISIBLE);
                ranking.setEnabled(true);
                ranking.setVisibility(View.VISIBLE);
                compo.setEnabled(true);
                compo.setVisibility(View.VISIBLE);
                newGameButton.setVisibility(View.VISIBLE);
                newGameButton.setEnabled(true);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // Nothing to do here
            }
            @Override
            public void onDrawerStateChanged(int newState) {
                // Nothing to do here
            }
        });


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = null;
        if (user != null) {
            userEmail = user.getEmail();
        }
        newGameButton = findViewById(R.id.BT_New_Game);
        if (userEmail != null && !userEmail.equals("claudiuppdc7@yahoo.com")) {
            newGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Only the referee can create a new game", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.volume:
                    if (isSoundEnabled) {
                        // Désactiver le son
                        isSoundEnabled = false;
                        Intent intent = new Intent(Season3.this, MyMusicService.class);
                        startService(intent);
                        item.setIcon(R.drawable.volume_off);
                        item.setTitle("Volume off");
                    } else {
                        // Réactiver le son
                        isSoundEnabled = true;
                        Intent intent = new Intent(Season3.this, MyMusicService.class);
                        stopService(intent);
                        item.setIcon(R.drawable.volume_up);
                        item.setTitle("Volume up");
                    }
                    break;
                case R.id.nav_about:
                    startActivity(new Intent(Season3.this, Contact.class));
                    break;

                case R.id.nav_logout:
                    startActivity(new Intent(Season3.this, LoginActivity.class));
                    Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                    finishAffinity(); // Empêcher l'utilisateur de revenir en arrière
                    break;

                case R.id.profile:
                    startActivity(new Intent(Season3.this, UploadActivity.class));
                    break;

            }
            newGameButton.setEnabled(false);
            return true;
        }

    public void goToLive(View v){
        startActivity(new Intent(Season3.this, OldGame.class));
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