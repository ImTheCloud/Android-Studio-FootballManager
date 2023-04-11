package Statistics;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.draredebosanci.R;
import Home.Home;

public class Statistics extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Statistics.this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToFormDany(View v){
        startActivity(new Intent(Statistics.this, StatDany.class));
    }
    public void goToFormRuben(View v){
        startActivity(new Intent(Statistics.this, StatRuben.class));
    }
    public void goToFormClaudiu(View v){
        startActivity(new Intent(Statistics.this, StatClaudiu.class));
    }
    public void goToFormFlavyus(View v){
        startActivity(new Intent(Statistics.this, StatFlavyus.class));
    }
    public void goToFormDenis(View v){
        startActivity(new Intent(Statistics.this, StatDenis.class));
    }
    public void goToFormRoberto(View v){
        startActivity(new Intent(Statistics.this, StatRoberto.class));
    }
    public void goToFormLucian(View v){
        startActivity(new Intent(Statistics.this, StatLucian.class));
    }
    public void goToFormDavid(View v){
        startActivity(new Intent(Statistics.this, StatDavid.class));
    }
    public void goToFormYaniv(View v){
        startActivity(new Intent(Statistics.this, StatYaniv.class));
    }
    public void goToFormIosif(View v){
        startActivity(new Intent(Statistics.this, StatIosif.class));
    }
    public void goToFormSimon(View v){
        startActivity(new Intent(Statistics.this, StatSimon.class));
    }
    public void goToFormEduard(View v){
        startActivity(new Intent(Statistics.this, StatEduard.class));
    }
    public void goToFormRank(View v){
        startActivity(new Intent(Statistics.this, Ranking.class));
    }





}