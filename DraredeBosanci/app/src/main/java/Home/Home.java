package Home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Firebase.LoginActivity;
import Form.Form;

public class Home extends AppCompatActivity {
    private int numTries = 0;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();


        Button btNewSeason = findViewById(R.id.BT_New_Season);
        // Vérifier si l'utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // Si l'utilisateur n'est pas connecté, afficher une alerte et ne pas passer à la méthode onClick
            btNewSeason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "You are not logged in", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Si l'utilisateur est connecté, le bouton fonctionnera normalement
            btNewSeason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Home.this, OldGame.class));

                }
            });
        }



        Button btSeason3DrareDeBosanci = findViewById(R.id.BT_Season3_DrareDeBosanci);

        btSeason3DrareDeBosanci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Créer une boîte de dialogue pour demander le code
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Enter the code");

                final EditText input = new EditText(Home.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String code = input.getText().toString();
                        if (code.equals("1070")) {
                            // Lancer l'activité si le code est correct
                            Intent intent = new Intent(Home.this, Season3.class);
                            startActivity(intent);
                        } else {
                            if (numTries < 1) {
                                // Afficher le nombre d'essais restants
                                int remainingTries = 1 - numTries;
                                Toast.makeText(Home.this, "Try again, remaining tries: " + remainingTries, Toast.LENGTH_SHORT).show();
                                numTries++;
                            } else {
                                // Bloquer le bouton et attendre 10 secondes avant de réinitialiser le nombre d'essais
                                btSeason3DrareDeBosanci.setEnabled(false);
                                Toast.makeText(Home.this, "The access is blocked please wait 10 seconds " , Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        btSeason3DrareDeBosanci.setEnabled(true);
                                        numTries = 0;
                                    }
                                }, 10000); // 10 secondes d'attente
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        EditText searchEditText = findViewById(R.id.search_edit_text);
        Button Season3= findViewById(R.id.BT_Season3_DrareDeBosanci);
        Button Season2 = findViewById(R.id.BT_Season2_DrareDeBosanci);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("Season 3")) {
                    Season3.setVisibility(View.VISIBLE);
                } else if (s.toString().equals("Season 2")) {
                    Season3.setVisibility(View.INVISIBLE);
                    Season2.setVisibility(View.VISIBLE);
                } else {
                    Season2.setVisibility(View.INVISIBLE);
                    Season3.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    public void goToLogin(View v){
        startActivity(new Intent(Home.this, LoginActivity.class));
    }

    protected void onStop() {
        super.onStop();
        mAuth.signOut();
    }


}
