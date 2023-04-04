package Compo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.draredebosanci.R;

import Home.Home;
import Home.NewGame;

public class Compo442 extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compo442);
        imageView = findViewById(R.id.imageView2);

        Configuration configuration = getResources().getConfiguration();
        if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE
                || (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            imageView.setImageResource(R.drawable.fieldcompo_tablet);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Compo442.this, Compo.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }


}