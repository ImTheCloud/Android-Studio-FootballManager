package Home;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.draredebosanci.R;

public class Rules extends AppCompatActivity {
    private Button fr;
    private TextView textTitle,text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_rules);

        textTitle = findViewById(R.id.textTitle);
        text= findViewById(R.id.text);
        fr = findViewById(R.id.fr);

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr.setVisibility(View.INVISIBLE);
                textTitle.setText("Regles");
                text.setText("Temps de jeu :\n• Un temps de 1h36 par match est préférable.\n• Tout match doit durer au minimum 1h15.\n\nQuand il n'y a pas de buts :\n• Tous les buts douteux ne sont pas validés.\n• Si le ballon sort du terrain, même s'il était sur la ligne du but, le but n'est pas validé.\n• Si le poteau tombe, le but n'est pas validé.\n\nAbandonner le match :\n• 2 cartons jaunes pour le joueur qui sort.\n• Pour chaque 2 minutes restantes, il y a plus 1 but.\n• Le match est déclaré abandonné quand tous les joueurs ont quitté le terrain.\n\nTirage au sort :\n• Le match sera officiellement toujours tiré au sort une seule fois.\n• Un seul échange sera possible si les 2 joueurs sont d'accord.\n• Tous les joueurs sont tenus de respecter la composition de l'équipe et de venir jouer, sous peine de ne plus participer au match en question s'ils décident de ne pas venir pour des raisons d'équipe.\n• Si une personne ne peut plus venir avant que le match ait commencé, le match doit être retiré au sort.\n• Vasi joue à pierre-feuille-ciseaux, à moins que les 2 équipes s'accordent pour le donner à une équipe.\n\nSi les 2 équipes sont d'accord et qu'il y a une égalité, les 2 équipes peuvent jouer la victoire sur des tirs au but avec le nombre de tirs équivalent au nombre de joueurs dans chaque équipe.\n\n");

            }

        });
    }

}