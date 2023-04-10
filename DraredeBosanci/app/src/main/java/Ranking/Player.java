package Ranking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.draredebosanci.R;

import Home.Home;

public class Player extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


/////// on create end
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Player.this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToFormDany(View v){
        startActivity(new Intent(Player.this, RankDany.class));
    }
    public void goToFormRuben(View v){
        startActivity(new Intent(Player.this, RankRuben.class));
    }
    public void goToFormClaudiu(View v){
        startActivity(new Intent(Player.this, RankClaudiu.class));
    }
    public void goToFormFlavyus(View v){
        startActivity(new Intent(Player.this, RankFlavyus.class));
    }
    public void goToFormDenis(View v){
        startActivity(new Intent(Player.this, RankDenis.class));
    }
    public void goToFormRoberto(View v){
        startActivity(new Intent(Player.this, RankRoberto.class));
    }
    public void goToFormLucian(View v){
        startActivity(new Intent(Player.this, RankLucian.class));
    }
    public void goToFormDavid(View v){
        startActivity(new Intent(Player.this, RankDavid.class));
    }
    public void goToFormYaniv(View v){
        startActivity(new Intent(Player.this, RankYaniv.class));
    }
    public void goToFormIosif(View v){
        startActivity(new Intent(Player.this, RankIosif.class));
    }
    public void goToFormSimon(View v){
        startActivity(new Intent(Player.this, RankSimon.class));
    }
    public void goToFormEduard(View v){
        startActivity(new Intent(Player.this, RankEduard.class));
    }
    public void goToFormRank(View v){
        startActivity(new Intent(Player.this, Rank.class));
    }





}