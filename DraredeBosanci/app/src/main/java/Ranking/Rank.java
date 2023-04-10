package Ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Firebase.Form;
import Firebase.RankSave;

public class Rank extends AppCompatActivity {

    EditText name1, name2, name3, name4, name5, name6, name7, name8, name9, name10, name11, name12;
    EditText point1, point2, point3, point4, point5, point6, point7, point8, point9, point10, point11, point12;
    Button bt_Save;
    DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);

        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);
        point4 = findViewById(R.id.point4);
        point5 = findViewById(R.id.point5);
        point6 = findViewById(R.id.point6);
        point7 = findViewById(R.id.point7);
        point8 = findViewById(R.id.point8);
        point9 = findViewById(R.id.point9);
        point10 = findViewById(R.id.point10);
        point11 = findViewById(R.id.point11);
        point12 = findViewById(R.id.point12);

        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);
        name5 = findViewById(R.id.name5);
        name6 = findViewById(R.id.name6);
        name7 = findViewById(R.id.name7);
        name8 = findViewById(R.id.name8);
        name9 = findViewById(R.id.name9);
        name10 = findViewById(R.id.name10);
        name11 = findViewById(R.id.name11);
        name12 = findViewById(R.id.name12);

        TextView loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        bt_Save = findViewById(R.id.bt_Save);
        bt_Save.setVisibility(View.INVISIBLE);

        if (TextUtils.equals("claudiuppdc7@yahoo.com", FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            bt_Save.setVisibility(View.VISIBLE);

        }

        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RankSave data = new RankSave(name1, name2, name3, name4, name5, name6, name7, name8, name9, name10, name11, name12,point1, point2, point3, point4, point5, point6, point7, point8, point9, point10, point11, point12);
                    String uniqueId = "-rank"; // utiliser le même identifiant unique
                    UserRef = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Rank");
                    UserRef.child(uniqueId).setValue(data);
                    Toast.makeText(Rank.this, "Ranking save", Toast.LENGTH_SHORT).show();

            }
        });

    }
}