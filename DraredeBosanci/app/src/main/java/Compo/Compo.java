package Compo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.draredebosanci.R;

public class Compo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compo);

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