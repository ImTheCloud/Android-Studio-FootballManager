package Live;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.draredebosanci.R;

public class ChoiceGoal extends AppCompatActivity {
    private TextView[] tvGoals;
    private int[] counters;
    private Button[] buttons;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_goal);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset all counters to 0
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                for (int i = 0; i < tvGoals.length; i++) {
                    counters[i] = 0;
                    editor.putInt("counter" + getPlayerName(i), 0);
                    tvGoals[i].setText("0");
                }
                editor.apply();
            }
        });


        tvGoals = new TextView[]{
                findViewById(R.id.TV_DANY),
                findViewById(R.id.TV_CLAUDIU),
                findViewById(R.id.TV_RUBEN),
                findViewById(R.id.TV_FLAVYUS),
                findViewById(R.id.TV_DENIS),
                findViewById(R.id.TV_ROBERTO),
                findViewById(R.id.TV_LUCIAN),
                findViewById(R.id.TV_DAVID),
                findViewById(R.id.TV_SIMON),
                findViewById(R.id.TV_YANIV),
                findViewById(R.id.TV_EDUARD),
                findViewById(R.id.TV_IOSIF),
                findViewById(R.id.TV_OTHER1),
                findViewById(R.id.TV_OTHER2),
        };

        counters = new int[tvGoals.length]; // initialize the counters array

        // Load the saved values from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        for (int i = 0; i < tvGoals.length; i++) {
            String playerName = getPlayerName(i);
            int savedCounter = prefs.getInt("counter" + playerName, 0);
            counters[i] = savedCounter;
            tvGoals[i].setText(String.valueOf(savedCounter));
        }

        buttons = new Button[]{
                findViewById(R.id.BT_Dany),
                findViewById(R.id.BT_Claudiu),
                findViewById(R.id.BT_Ruben),
                findViewById(R.id.BT_Flavyus),
                findViewById(R.id.BT_Denis),
                findViewById(R.id.BT_Roberto),
                findViewById(R.id.BT_Lucian),
                findViewById(R.id.BT_David),
                findViewById(R.id.BT_Simon),
                findViewById(R.id.BT_Yaniv),
                findViewById(R.id.BT_Eduard),
                findViewById(R.id.BT_Iosif),
                findViewById(R.id.BT_Other1),
                findViewById(R.id.BT_Other2),

        };

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counters[index]++;
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("counter" + getPlayerName(index), counters[index]);
                    editor.apply();

                    int currentGoal = Integer.parseInt(tvGoals[index].getText().toString());
                    currentGoal++;
                    tvGoals[index].setText(String.valueOf(currentGoal));
                    onBackPressed();
                }
            });
        }
    }
// on create end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String getPlayerName(int index) {
        switch (index) {
            case 0:
                return "Dany";
            case 1:
                return "Claudiu";
            case 2:
                return "Ruben";
            case 3:
                return "Flavyus";
            case 4:
                return "Denis";
            case 5:
                return "Roberto";
            case 6:
                return "Lucian";
            case 7:
                return "David";
            case 8:
                return "Simon";
            case 9:
                return "Yaniv";
            case 10:
                return "Eduard";
            case 11:
                return "Iosif";
            case 12:
                return "Other1";
            case 13:
                return "Other2";
        }
        return null;
    }

//    public void createNewButton(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Name of the player :");
//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        builder.setView(input);
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String buttonText = input.getText().toString();
//
//                // Create a new LinearLayout to hold the TextView and Button
//                LinearLayout newLayout = new LinearLayout(ChoiceGoal.this);
//                newLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//                // Create a new TextView and set its text to "0"
//                TextView newTextView = new TextView(ChoiceGoal.this);
//                newTextView.setText("0");
//                newTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//                newTextView.setTextColor(Color.WHITE);
//                newTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//
//                // Create a new button and set its text and background color
//                Button newButton = new Button(ChoiceGoal.this);
//                newButton.setText(buttonText);
//                newButton.setBackgroundColor(Color.parseColor("#004CAF50"));
//                newButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.person, 0, 0, 0);
//
//                // Set layout parameters for the TextView and Button
//                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                );
//                textParams.setMargins(10, 10, 10, 10);
//                newTextView.setLayoutParams(textParams);
//
//                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        1.0f
//                );
//                buttonParams.setMargins(10, 10, 10, 10);
//                newButton.setLayoutParams(buttonParams);
//
//                // Get the parent layout and set its orientation to horizontal
//                LinearLayout parentLayout = findViewById(R.id.layoutNew);
//                parentLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//                // Add the new TextView and Button to the new LinearLayout
//                newLayout.addView(newTextView);
//                newLayout.addView(newButton);
//
//                // Add the new LinearLayout to the parent layout at the end
//                parentLayout.addView(newLayout, parentLayout.getChildCount());
//
//                // Set the layout params of the previous LinearLayout to have equal weight
//                if (parentLayout.getChildCount() > 1) {
//                    LinearLayout prevLayout = (LinearLayout) parentLayout.getChildAt(parentLayout.getChildCount() - 2);
//                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) prevLayout.getLayoutParams();
//                    params.weight = 1.0f;
//                    prevLayout.setLayoutParams(params);
//                }
//
//                // Increment the TextView value when the new button is clicked
//                newButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int currentValue = Integer.parseInt(newTextView.getText().toString());
//                        newTextView.setText(String.valueOf(currentValue + 1));
//                    }
//                });
//            }
//        });
//
//        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//
//    }


}
