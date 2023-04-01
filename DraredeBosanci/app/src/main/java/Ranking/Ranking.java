package Ranking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.draredebosanci.R;

public class Ranking extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


/////// on create end
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    public void goToFormDany(View v){
        startActivity(new Intent(Ranking.this, FormDany.class));
    }
    public void goToFormRuben(View v){
        startActivity(new Intent(Ranking.this, FormRuben.class));
    }
    public void goToFormClaudiu(View v){
        startActivity(new Intent(Ranking.this, RankClaudiu.class));
    }
    public void goToFormFlavyus(View v){
        startActivity(new Intent(Ranking.this, FormFlavyus.class));
    }
    public void goToFormDenis(View v){
        startActivity(new Intent(Ranking.this, FormDenis.class));
    }
    public void goToFormRoberto(View v){
        startActivity(new Intent(Ranking.this, FormRoberto.class));
    }
    public void goToFormLucian(View v){
        startActivity(new Intent(Ranking.this, FormLucian.class));
    }
    public void goToFormDavid(View v){
        startActivity(new Intent(Ranking.this, FormDavid.class));
    }
    public void goToFormYaniv(View v){
        startActivity(new Intent(Ranking.this, FormYaniv.class));
    }
    public void goToFormIosif(View v){
        startActivity(new Intent(Ranking.this, FormIosif.class));
    }
    public void goToFormSimon(View v){
        startActivity(new Intent(Ranking.this, FormSimon.class));
    }
    public void goToFormEduard(View v){
        startActivity(new Intent(Ranking.this, FormEduard.class));
    }




}