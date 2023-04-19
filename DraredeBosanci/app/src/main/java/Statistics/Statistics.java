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

    public void goToStat(View v){
        startActivity(new Intent(Statistics.this, Stats.class));
    }

    public void goToFormRank(View v){
        startActivity(new Intent(Statistics.this, Ranking.class));
    }





}