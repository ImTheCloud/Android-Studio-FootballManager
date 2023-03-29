package Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import Firebase.Login;
import Music.MyMusicService;
import com.example.draredebosanci.R;
import Compo.CompoChoice;
import Form.Form;
import Team.TeamSelection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.drawerlayout.widget.DrawerLayout;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isSoundEnabled = true;
    private DrawerLayout drawerLayout;
    FloatingActionButton compo;
    private Button newGameButton,oldGame,liveGame,ranking;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        oldGame = findViewById(R.id.BT_History);
        ranking = findViewById(R.id.BT_Ranking);
        compo = findViewById(R.id.BT_Compo);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        View header = navigationView.getHeaderView(0);
        TextView navEmail = (TextView) header.findViewById(R.id.navEmail);


        mAuth = FirebaseAuth.getInstance();
        // Vérifier si l'utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Si l'utilisateur n'est pas connecté, afficher une alerte
            Toast.makeText(getApplicationContext(), "You are not logged in", Toast.LENGTH_SHORT).show();
            navEmail.setText("No one is connected"); // Afficher "Personne n'est connecté" dans le TextView
        } else {
            // Si l'utilisateur est connecté, afficher son nom dans le TextView
            navEmail.setText("Connected as " + currentUser.getEmail());
        }

            setSupportActionBar(toolbar);
            navigationView.setNavigationItemSelectedListener(this);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();


            drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                    newGameButton.setVisibility(View.INVISIBLE);
                    newGameButton.setEnabled(false);
                    oldGame.setEnabled(false);
                    oldGame.setVisibility(View.INVISIBLE);
                    ranking.setEnabled(false);
                    ranking.setVisibility(View.INVISIBLE);
                    compo.setEnabled(false);
                    compo.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onDrawerClosed(@NonNull View drawerView) {
                    oldGame.setEnabled(true);
                    oldGame.setVisibility(View.VISIBLE);
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


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            String userEmail = null;
            if (user != null) {
                userEmail = user.getEmail();
            }

            newGameButton = findViewById(R.id.BT_New_Game);

            if (userEmail != null) {
                newGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Home.this, TeamSelection.class));

                    }
                });
            } else {
                newGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Connect First", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            compo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Home.this, CompoChoice.class);
                    startActivity(intent);
                }
            });
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
                        Intent intent = new Intent(Home.this, MyMusicService.class);
                        startService(intent);
                        item.setIcon(R.drawable.volume_off);
                        item.setTitle("Volume off");
                    } else {
                        // Réactiver le son
                        isSoundEnabled = true;
                        Intent intent = new Intent(Home.this, MyMusicService.class);
                        stopService(intent);
                        item.setIcon(R.drawable.volume_up);
                        item.setTitle("Volume up");
                    }
                    break;
                case R.id.nav_about:
                    startActivity(new Intent(Home.this, Contact.class));
                    break;

                case R.id.nav_logout:
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    startActivity(new Intent(Home.this, Login.class));

                case R.id.nav_login:
                    startActivity(new Intent(Home.this, Login.class));

                    break;

            }
            newGameButton.setEnabled(false);
            return true;
        }



    public void goToHistory(View v){
        startActivity(new Intent(Home.this, History.class));
    }
    public void goToRanking(View v){
        startActivity(new Intent(Home.this, Form.class));
    }

}