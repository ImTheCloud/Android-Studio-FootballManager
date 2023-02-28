package com.example.draredebosanci;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Form extends AppCompatActivity {
    //private Button newButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

    }
    public void goToFormDany(View v){
        startActivity(new Intent(Form.this, FormDany.class));
    }
    public void goToFormRuben(View v){
        startActivity(new Intent(Form.this, FormRuben.class));
    }
    public void goToFormClaudiu(View v){
        startActivity(new Intent(Form.this, FormClaudiu.class));
    }
    public void goToFormFlavyus(View v){
        startActivity(new Intent(Form.this, FormFlavyus.class));
    }
    public void goToFormDenis(View v){
        startActivity(new Intent(Form.this, FormDenis.class));
    }
    public void goToFormRoberto(View v){
        startActivity(new Intent(Form.this, FormRoberto.class));
    }
    public void goToFormLucian(View v){
        startActivity(new Intent(Form.this, FormLucian.class));
    }
    public void goToFormDavid(View v){
        startActivity(new Intent(Form.this, FormDavid.class));
    }
    public void goToFormYaniv(View v){
        startActivity(new Intent(Form.this, FormYaniv.class));
    }
    public void goToFormIosif(View v){
        startActivity(new Intent(Form.this, FormIosif.class));
    }
    public void goToFormSimon(View v){
        startActivity(new Intent(Form.this, FormSimon.class));
    }
    public void goToFormEduard(View v){
        startActivity(new Intent(Form.this, FormEduard.class));
    }

   /* public void createNewButton(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name of the new player :");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String buttonText = input.getText().toString();

                // Create a new button and set its text and background color
                Button newButton = new Button(Form.this);
                newButton.setText(buttonText);
                newButton.setBackgroundColor(Color.parseColor("#071227"));
                // Add the button to the parent layout
                LinearLayout parentLayout = findViewById(R.id.parent_layout);
                parentLayout.addView(newButton);

                // Set layout parameters for the button
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                int marginInDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                layoutParams.setMargins(0, marginInDp, 0, marginInDp);
                newButton.setLayoutParams(layoutParams);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }*/



}