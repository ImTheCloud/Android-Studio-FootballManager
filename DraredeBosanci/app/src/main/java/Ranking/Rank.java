package Ranking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.draredebosanci.R;

public class Rank extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);


/////// on create end
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    public void goToFormDany(View v){
        startActivity(new Intent(Rank.this, FormDany.class));
    }
    public void goToFormRuben(View v){
        startActivity(new Intent(Rank.this, RankRuben.class));
    }
    public void goToFormClaudiu(View v){
        startActivity(new Intent(Rank.this, RankClaudiu.class));
    }
    public void goToFormFlavyus(View v){
        startActivity(new Intent(Rank.this, FormFlavyus.class));
    }
    public void goToFormDenis(View v){
        startActivity(new Intent(Rank.this, FormDenis.class));
    }
    public void goToFormRoberto(View v){
        startActivity(new Intent(Rank.this, FormRoberto.class));
    }
    public void goToFormLucian(View v){
        startActivity(new Intent(Rank.this, FormLucian.class));
    }
    public void goToFormDavid(View v){
        startActivity(new Intent(Rank.this, FormDavid.class));
    }
    public void goToFormYaniv(View v){
        startActivity(new Intent(Rank.this, FormYaniv.class));
    }
    public void goToFormIosif(View v){
        startActivity(new Intent(Rank.this, FormIosif.class));
    }
    public void goToFormSimon(View v){
        startActivity(new Intent(Rank.this, FormSimon.class));
    }
    public void goToFormEduard(View v){
        startActivity(new Intent(Rank.this, FormEduard.class));
    }




}