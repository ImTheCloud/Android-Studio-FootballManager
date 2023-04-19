package Home;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import Firebase.Login;
import Firebase.Register;
import Music.MyMusicService;
import com.example.draredebosanci.R;
import Compo.Compo;
import Statistics.Statistics;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.drawerlayout.widget.DrawerLayout;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isSoundEnabled = true;
    private DrawerLayout drawerLayout;
    private Button newGameButton,history,statistics;
    private FirebaseAuth mAuth;
    private ImageView imageView;
    private TextView navEmail;
    private Toolbar toolbar;
    private View header;
    private LinearLayout layout_home;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        history = findViewById(R.id.BT_History);
        statistics = findViewById(R.id.BT_Statistics);
        newGameButton = findViewById(R.id.BT_New_Game);
        drawerLayout = findViewById(R.id.drawer_layout);
        layout_home = findViewById(R.id.layout_home);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        header = navigationView.getHeaderView(0);
        navEmail = (TextView) header.findViewById(R.id.navEmail);

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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            navEmail.setText("No user currently connected");
        } else {
            navEmail.setText(currentUser.getEmail());
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
                statistics.setEnabled(false);
                statistics.setVisibility(View.INVISIBLE);

                layout_home.setEnabled(false);
                layout_home.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                history.setEnabled(true);
                history.setVisibility(View.VISIBLE);
                statistics.setEnabled(true);
                statistics.setVisibility(View.VISIBLE);

                newGameButton.setVisibility(View.VISIBLE);
                newGameButton.setEnabled(true);
                layout_home.setVisibility(View.VISIBLE);
                layout_home.setEnabled(true);
            }
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = null;
        if (user != null) {
            userEmail = user.getEmail();
        }

        if (userEmail != null) {
            statistics.setOnClickListener(view -> startActivity(new Intent(Home.this, Statistics.class)));
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Home.this, History.class));
                }
            });
        } else {
            statistics.setOnClickListener(new View.OnClickListener() {

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
                    isSoundEnabled = false;
                    Intent intent = new Intent(Home.this, MyMusicService.class);
                    startService(intent);
                    item.setIcon(R.drawable.volume_off);
                    item.setTitle("Volume off");
                } else {
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
                if (userEmail != null) {
                    startActivity(new Intent(Home.this, Compo.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Toast.makeText(getApplicationContext(), "Log in First", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_help:
                startActivity(new Intent(Home.this, Help.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.nav_register:
                if (userEmail != null) {
                    Toast.makeText(getApplicationContext(), "Log out First", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(Home.this, Register.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
            case R.id.nav_rules:
                startActivity(new Intent(Home.this, Rules.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
        newGameButton.setEnabled(false);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
    public void goToNewGame(View v) {
        startActivity(new Intent(Home.this, NewGame.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

