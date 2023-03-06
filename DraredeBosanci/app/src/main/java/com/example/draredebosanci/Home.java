package com.example.draredebosanci;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends AppCompatActivity {

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
                builder.setTitle("Enter code");

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
                            Toast.makeText(Home.this, "Wrong code, try again", Toast.LENGTH_SHORT).show();
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