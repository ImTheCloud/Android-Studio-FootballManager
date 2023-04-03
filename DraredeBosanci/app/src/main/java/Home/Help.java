package Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.draredebosanci.R;

import Ranking.Rank;
import Ranking.RankClaudiu;

public class Help extends AppCompatActivity {

    private Button aboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_help);
        aboutMe = findViewById(R.id.aboutMe);


        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, AboutMe.class);
                startActivity(intent);
            }
        });

    }

}
