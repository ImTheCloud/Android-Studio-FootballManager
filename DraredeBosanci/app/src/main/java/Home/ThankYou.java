package Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.draredebosanci.R;

public class ThankYou extends AppCompatActivity {

    private Button fr;
    private TextView ABOUTTEXT,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_thank_you);

        ABOUTTEXT = findViewById(R.id.ABOUTTEXT);
        text= findViewById(R.id.text);
        fr = findViewById(R.id.fr);

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr.setVisibility(View.INVISIBLE);
                ABOUTTEXT.setText("Remerciement");
                text.setText("Je voudrais m'adresser à Monsieur Riggio et lui dire un grand merci de m'avoir guidé dans ce projet.");
            }
        });

    }
}