package Ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.draredebosanci.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private LinearLayout parent_layout;

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

        parent_layout = findViewById(R.id.parent_layout);
        parent_layout.setVisibility(View.INVISIBLE);


        EditText[] editTextArray = new EditText[] {
                findViewById(R.id.name1),
                findViewById(R.id.name2),
                findViewById(R.id.name3),
                findViewById(R.id.name4),
                findViewById(R.id.name5),
                findViewById(R.id.name6),
                findViewById(R.id.name7),
                findViewById(R.id.name8),
                findViewById(R.id.name9),
                findViewById(R.id.name10),
                findViewById(R.id.name11),
                findViewById(R.id.name12)
        };

        for (EditText editText : editTextArray) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        ((EditText) view).setText("");
                    }
                }
            });
        }



        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drare-de-bosanci-default-rtdb.europe-west1.firebasedatabase.app/");
        database.getReference("Rank").child("-rank").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    parent_layout.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    String pt1 = snapshot.child("point1").getValue(String.class);
                    String pt2 = snapshot.child("point2").getValue(String.class);
                    String pt3 = snapshot.child("point3").getValue(String.class);
                    String pt4 = snapshot.child("point4").getValue(String.class);
                    String pt5 = snapshot.child("point5").getValue(String.class);
                    String pt6 = snapshot.child("point6").getValue(String.class);
                    String pt7 = snapshot.child("point7").getValue(String.class);
                    String pt8 = snapshot.child("point8").getValue(String.class);
                    String pt9= snapshot.child("point9").getValue(String.class);
                    String pt10 = snapshot.child("point10").getValue(String.class);
                    String pt11 = snapshot.child("point11").getValue(String.class);
                    String pt12 = snapshot.child("point12").getValue(String.class);

                    String nm1 = snapshot.child("name1").getValue(String.class);
                    String nm2 = snapshot.child("name2").getValue(String.class);
                    String nm3 = snapshot.child("name3").getValue(String.class);
                    String nm4 = snapshot.child("name4").getValue(String.class);
                    String nm5 = snapshot.child("name5").getValue(String.class);
                    String nm6 = snapshot.child("name6").getValue(String.class);
                    String nm7 = snapshot.child("name7").getValue(String.class);
                    String nm8 = snapshot.child("name8").getValue(String.class);
                    String nm9= snapshot.child("name9").getValue(String.class);
                    String nm10 = snapshot.child("name10").getValue(String.class);
                    String nm11 = snapshot.child("name11").getValue(String.class);
                    String nm12 = snapshot.child("name12").getValue(String.class);

                    name1.setText(nm1);
                    name2.setText(nm2);
                    name3.setText(nm3);
                    name4.setText(nm4);
                    name5.setText(nm5);
                    name6.setText(nm6);
                    name7.setText(nm7);
                    name8.setText(nm8);
                    name9.setText(nm9);
                    name10.setText(nm10);
                    name11.setText(nm11);
                    name12.setText(nm12);

                    point1.setText(pt1);
                    point2.setText(pt2);
                    point3.setText(pt3);
                    point4.setText(pt4);
                    point5.setText(pt5);
                    point6.setText(pt6);
                    point7.setText(pt7);
                    point8.setText(pt8);
                    point9.setText(pt9);
                    point10.setText(pt10);
                    point11.setText(pt11);
                    point12.setText(pt12);

                }

                else{
                    loading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (TextUtils.equals("claudiuppdc7@yahoo.com", FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            bt_Save.setVisibility(View.VISIBLE);
        }else{
            name1.setEnabled(false);
            name2.setEnabled(false);
            name3.setEnabled(false);
            name4.setEnabled(false);
            name5.setEnabled(false);
            name6.setEnabled(false);
            name7.setEnabled(false);
            name8.setEnabled(false);
            name9.setEnabled(false);
            name10.setEnabled(false);
            name11.setEnabled(false);
            name12.setEnabled(false);

            point1.setEnabled(false);
            point2.setEnabled(false);
            point3.setEnabled(false);
            point4.setEnabled(false);
            point5.setEnabled(false);
            point6.setEnabled(false);
            point7.setEnabled(false);
            point8.setEnabled(false);
            point9.setEnabled(false);
            point10.setEnabled(false);
            point11.setEnabled(false);
            point12.setEnabled(false);
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