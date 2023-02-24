package com.example.draredebosanci;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Ranking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
    }

    private Button newButton;

    public void createNewButton(View view) {
        // Demander à l'utilisateur de saisir un nom pour le bouton
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name of the new player :");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Définir le texte du bouton sur le nom saisi par l'utilisateur
                String buttonText = input.getText().toString();
                newButton = new Button(Ranking.this);
                newButton.setText(buttonText);

                // Définir les paramètres de layout avec des marges supérieure et inférieure
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                int marginInDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                layoutParams.setMargins(0, marginInDp, 0, marginInDp);
                newButton.setLayoutParams(layoutParams);

                // Mettre la couleur de fond en bleu foncé et réduire l'espace entre les boutons
                newButton.setBackgroundColor(Color.parseColor("#071227"));
                LinearLayout parentLayout = findViewById(R.id.parent_layout);
                parentLayout.setPadding(0, 0, 0, marginInDp);

                // Ajouter le nouveau bouton au layout parent
                parentLayout.addView(newButton);
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








    public void goToClaudiuForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToRubenForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToDenisForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToFlavyusForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToRobertoForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToEduardForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToDavidForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToLucianForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }
    public void goToYanivForm(View v){
        startActivity(new Intent(Ranking.this, ClaudiuForm.class));
    }


}