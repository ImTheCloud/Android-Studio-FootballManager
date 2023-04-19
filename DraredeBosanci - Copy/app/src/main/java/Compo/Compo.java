package Compo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;

import com.example.draredebosanci.R;

import Home.Home;

public class Compo extends AppCompatActivity {
    private ImageView imageView;
    private Space space;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compo);

        imageView = findViewById(R.id.imageView2);
        space = findViewById(R.id.space);

        Configuration configuration = getResources().getConfiguration();
        if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE
                || (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            imageView.setImageResource(R.drawable.fieldcompo_tablet);
        }else{
            ViewGroup.LayoutParams layoutParams = space.getLayoutParams();
            layoutParams.width = (int) getResources().getDimension(R.dimen.space_width);
            space.setLayoutParams(layoutParams);
        }
    }
    public void goTo442(View v){
        startActivity(new Intent(this, Compo442.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void goTo433(View v){
        startActivity(new Intent(this, Compo433.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}