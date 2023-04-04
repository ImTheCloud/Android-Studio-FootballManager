package Ranking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.draredebosanci.R;

import Home.Home;

public class Rank extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);



/////// on create end
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Rank.this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToFormDany(View v){
        startActivity(new Intent(Rank.this, RankDany.class));
    }
    public void goToFormRuben(View v){
        startActivity(new Intent(Rank.this, RankRuben.class));
    }
    public void goToFormClaudiu(View v){
        startActivity(new Intent(Rank.this, RankClaudiu.class));
    }
    public void goToFormFlavyus(View v){
        startActivity(new Intent(Rank.this, RankFlavyus.class));
    }
    public void goToFormDenis(View v){
        startActivity(new Intent(Rank.this, RankDenis.class));
    }
    public void goToFormRoberto(View v){
        startActivity(new Intent(Rank.this, RankRoberto.class));
    }
    public void goToFormLucian(View v){
        startActivity(new Intent(Rank.this, RankLucian.class));
    }
    public void goToFormDavid(View v){
        startActivity(new Intent(Rank.this, RankDavid.class));
    }
    public void goToFormYaniv(View v){
        startActivity(new Intent(Rank.this, RankYaniv.class));
    }
    public void goToFormIosif(View v){
        startActivity(new Intent(Rank.this, RankIosif.class));
    }
    public void goToFormSimon(View v){
        startActivity(new Intent(Rank.this, RankSimon.class));
    }
    public void goToFormEduard(View v){
        startActivity(new Intent(Rank.this, RankEduard.class));
    }




}