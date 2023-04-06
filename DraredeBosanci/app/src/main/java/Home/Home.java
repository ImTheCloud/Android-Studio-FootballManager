package Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import Firebase.Login;
import Music.MyMusicService;
import com.example.draredebosanci.R;
import Compo.Compo;
import Notif.NotificationHelper;
import Ranking.Rank;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isSoundEnabled = true;
    private DrawerLayout drawerLayout;
    FloatingActionButton compo;
    private Button newGameButton,history,ranking;
    private FirebaseAuth mAuth;
    private ImageView imageView;

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        history = findViewById(R.id.BT_History);
        ranking = findViewById(R.id.BT_Ranking);
        newGameButton = findViewById(R.id.BT_New_Game);
        drawerLayout = findViewById(R.id.drawer_layout);
        LinearLayout layout_home = findViewById(R.id.layout_home);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        View header = navigationView.getHeaderView(0);
        TextView navEmail = (TextView) header.findViewById(R.id.navEmail);


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // format tablette
        imageView = findViewById(R.id.imageView);

        Configuration configuration = getResources().getConfiguration();
        if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE
                || (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            imageView.setImageResource(R.drawable.football_tablette);
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        mAuth = FirebaseAuth.getInstance();
        // Vérifier si l'utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Si l'utilisateur n'est pas connecté, afficher une alerte
            navEmail.setText("No one is connected"); // Afficher "Personne n'est connecté" dans le TextView
        } else {
            // Si l'utilisateur est connecté, afficher son nom dans le TextView
            navEmail.setText("Connected as :" +"\n"+ currentUser.getEmail());
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
                history.setEnabled(false);
                history.setVisibility(View.INVISIBLE);
                ranking.setEnabled(false);
                ranking.setVisibility(View.INVISIBLE);

                layout_home.setEnabled(false);
                layout_home.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                history.setEnabled(true);
                history.setVisibility(View.VISIBLE);
                ranking.setEnabled(true);
                ranking.setVisibility(View.VISIBLE);

                newGameButton.setVisibility(View.VISIBLE);
                newGameButton.setEnabled(true);
                layout_home.setVisibility(View.VISIBLE);
                layout_home.setEnabled(true);


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

        if (userEmail != null) {
            ranking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Home.this, Rank.class));

                }
            });
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Home.this, History.class));
                }
            });
        } else {
            ranking.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Log in First", Toast.LENGTH_SHORT).show();
                }
            });
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Log in First", Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = null;
        if (user != null) {
            userEmail = user.getEmail();
        }
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
                startActivity(new Intent(Home.this, AboutMe.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.nav_logout:

                if (userEmail != null) {
                    mAuth.signOut();
                    startActivity(new Intent(Home.this, Login.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                }else{
                    Toast.makeText(getApplicationContext(), "Already disconnected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_login:
                if (userEmail != null) {
                    Toast.makeText(getApplicationContext(), "Already connected", Toast.LENGTH_SHORT).show();

                }else{

                    startActivity(new Intent(Home.this, Login.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                }
                break;
            case R.id.nav_compo:
                startActivity(new Intent(Home.this, Compo.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                break;
            case R.id.nav_help:
                startActivity(new Intent(Home.this, Help.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                break;

        }
        newGameButton.setEnabled(false);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Home.this, Home.class);
        startActivity(intent);

    }
    public void goToNewGame(View v) {
        startActivity(new Intent(Home.this, NewGame.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

//        NotificationHelper notificationHelper = new NotificationHelper(this);
//        NotificationCompat.Builder builder = notificationHelper.createNotification("It's time to go play soccer :)");
//        builder.setSmallIcon(R.drawable.baseline_sports_soccer_24);
//        NotificationManager manager = notificationHelper.getManager();
//        manager.notify(1, builder.build());
    }



}

