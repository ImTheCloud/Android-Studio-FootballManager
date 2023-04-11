package Home;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.draredebosanci.R;

public class AboutMe extends AppCompatActivity {
    private Button fr;
    private TextView ABOUTTEXT,text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_about_me);

        ABOUTTEXT = findViewById(R.id.ABOUTTEXT);
        text= findViewById(R.id.text);
        fr = findViewById(R.id.fr);

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ABOUTTEXT.setText("A PROPOS DE MOI");
                text.setText("Je suis un jeune développeur passionné par les nouvelles technologies et l'informatique. " +
                        "\n\nJ'ai développé une application pour mon usage personnel, ce qui m'a pris environ un mois et demi." +
                        "\n\n Tout au long du processus, j'ai acquis des connaissances précieuses sur la conception de logiciels, les langages de programmation et l'expérience utilisateur. " +
                        "\n\nCette expérience a alimenté ma passion pour le développement de logiciels.\n\n");
            }
        });
    }

}