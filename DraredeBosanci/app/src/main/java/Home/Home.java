package Home;
import static Home.NewGame.date;
import static Home.NewGame.userLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import Firebase.Login;
import Firebase.Register;
import Live.GameSave;
import Music.MyMusicService;
import com.example.draredebosanci.R;
import Compo.Compo;
import Statistics.Statistics;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    private NavigationView navigationView;    private DatabaseReference UserRef;


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
            navEmail.setText("No user connected");
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Menu navMenu = navigationView.getMenu();
        MenuItem navLoginItem = navMenu.findItem(R.id.nav_login);
        MenuItem navLogoutItem = navMenu.findItem(R.id.nav_logout);
        MenuItem navRefereeItem = navMenu.findItem(R.id.nav_referee);

        if (user != null) {
            userEmail = user.getEmail();
            navLoginItem.setVisible(false);

        }else{
            navRefereeItem.setVisible(false);
            navLogoutItem.setVisible(false);
        }
    }

// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    item.setIcon(R.drawable.volume_up);
                    item.setTitle("Volume on");
                } else {
                    isSoundEnabled = true;
                    Intent intent = new Intent(Home.this, MyMusicService.class);
                    stopService(intent);
                    item.setIcon(R.drawable.volume_off);
                    item.setTitle("Volume off");
                }
                break;
            case R.id.nav_about:
                startActivity(new Intent(Home.this, AboutMe.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(Home.this, Login.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.nav_login:
                startActivity(new Intent(Home.this, Login.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
            case R.id.nav_gamePosition:
                if (userEmail != null) {
                    startActivity(new Intent(Home.this, Map.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Toast.makeText(getApplicationContext(), "Log in First", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.nav_meteo:
               startActivity(new Intent(Home.this, Meteo.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.nav_referee:
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                final EditText input = new EditText(Home.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setTitle("Enter a referee code").setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth = FirebaseAuth.getInstance();
                                FirebaseAuth mAuthMail = FirebaseAuth.getInstance();
                                FirebaseUser user = mAuthMail.getCurrentUser();
                                String userEmail = null;
                                String email = user.getEmail();

                                if (user != null) {
                                    userEmail = user.getEmail();
                                }

                                if (input.getText().toString().equals("add referee")) {
                                    AdmiSave data = new AdmiSave(email);
                                    UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Referee");
                                    Query query = UserRef.orderByValue().equalTo(email); // create a query to search for the email
                                    query.addListenerForSingleValueEvent(new ValueEventListener() { // add a listener to retrieve the search result
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) { // check if the email already exists
                                                Toast.makeText(Home.this, "You are already a Referee", Toast.LENGTH_SHORT).show();
                                            } else {
                                                UserRef.addListenerForSingleValueEvent(new ValueEventListener() { // add a listener to get the count of child nodes
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        long count = dataSnapshot.getChildrenCount(); // get the count of child nodes
                                                        String emailKey = "email" + (count + 1); // generate a unique email key
                                                        UserRef.child(emailKey).setValue(email); // set email address as the value under the email key

                                                        Toast.makeText(Home.this, "You are now a Referee", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        Toast.makeText(Home.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(Home.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else if(input.getText().toString().equals("delete referees")) {
                                    UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Referee");
                                    UserRef.removeValue();
                                    Toast.makeText(Home.this, "You have removed all the referees", Toast.LENGTH_SHORT).show();
                                }else if(input.getText().toString().equals("delete history")) {
                                    UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Game");
                                    UserRef.removeValue();
                                    Toast.makeText(Home.this, "You have deleted the entire history", Toast.LENGTH_SHORT).show();
                                }else if(input.getText().toString().equals("delete players")) {
                                    UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Player");
                                    UserRef.removeValue();
                                    Toast.makeText(Home.this, "You have deleted all players", Toast.LENGTH_SHORT).show();
                                }else if(input.getText().toString().equals("delete compos")) {
                                    UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Compo");
                                    UserRef.removeValue();
                                    Toast.makeText(Home.this, "You have deleted compos", Toast.LENGTH_SHORT).show();
                                }else if(input.getText().toString().equals("view referees")) {

                                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
                                    database.getReference("Referee").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                StringBuilder referees = new StringBuilder();
                                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                                    String refereeEmail = childSnapshot.getValue(String.class);
                                                    referees.append(refereeEmail).append("\n");
                                                }
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                                                builder.setTitle("Referees");
                                                builder.setMessage(referees.toString());
                                                builder.setPositiveButton("OK", null);
                                                builder.show();
                                            }


                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                }else if(input.getText().toString().equals("view version")) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                                    builder.setTitle("Version of the app");
                                    builder.setMessage("Version 1.0.0");
                                    builder.setPositiveButton("OK", null);
                                    builder.show();
                                }else if(input.getText().toString().equals("view codes")) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                                                builder.setTitle("Codes");
                                                builder.setMessage("view version\nview codes\nview referees\nadd referee\ndelete referees\ndelete history\ndelete players\ndelete compos");
                                                builder.setPositiveButton("OK", null);
                                                builder.show();

                                }else{
                                    Toast.makeText(Home.this, "Please enter correct code", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
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

