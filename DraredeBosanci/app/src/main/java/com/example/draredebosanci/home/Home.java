package com.example.draredebosanci.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.draredebosanci.R;

public class Home extends AppCompatActivity {
    private int numTries = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

    }

}
