package Home;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.draredebosanci.R;

public class Help extends AppCompatActivity {
    private Button aboutMe,fr;
    private TextView text,textTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_help);
        aboutMe = findViewById(R.id.aboutMe);
        fr = findViewById(R.id.fr);
        text= findViewById(R.id.text);
        textTitle= findViewById(R.id.textTitle);

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, AboutMe.class);
                startActivity(intent);
            }
        });

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutMe.setText("BESOIN PLUS D'AIDE ?");
                textTitle.setText("AIDE");
                fr.setVisibility(View.INVISIBLE);
                text.setText("\nAccès :\nAccès utilisateur non connecté / enregistré :\n"+
                       "-Accès à la création de partie :\n\n" +
                       "Accès de l'utilisateur connecté :\n" +
                       "-Seuls ces utilisateurs peuvent accéder aux pages d'historique et de profil.\n\n" +
                       "Accès administrateur :\n- L'administrateur est le seul à pouvoir sauvegarder des parties.\n" +
                       "-Seul l'administrateur peut sauvegarder les parties.\n" +
                       "-Seul l'administrateur peut modifier les profils des joueurs.\n" +
                       "-Tous les accès.\n" +
                       "\n" +
                       "Wifi :\n" +
                       "Le wifi est nécessaire pour :\n- Sauvegarder un jeu.\n" +
                       "-Sauvegarder une partie.\n" +
                       "-Consulter l'historique.\n"+
                       "-S'inscrire et se connecter.\n" +
                       "-Voir et modifier les profils.\n" +
                       "\n" +
                       "Inscription/Connexion :\n" +
                       "-Lors de votre inscription, vous recevrez un e-mail de confirmation que vous devrez confirmer pour accéder à l'application.\n" +
                       "-Si vous ne vérifiez pas votre e-mail, vous ne pourrez pas accéder à l'application en tant qu'utilisateur connecté.\n\n" +

                       "Règle de bonne pratique :\n" +
                       "Classement :\n" +
                       "-Il est possible de passer d'un profil à l'autre.\n" +
                       "-Lorsque vous enregistrez un profil pour la première fois, vous devez l'enregistrer puis revenir à la page.\n" +
                       "\n" +
                       "Nouvelle partie :\n- Il y a la possibilité de glisser entre les profils.\n" +
                       "-Les marqueurs verts sur la carte sont les endroits où les parties sauvegardées ont été jouées.\n" +
                       "-La température peut être affichée en fonction du nom de la ville indiquée.\n" +
                       "-Vous pouvez choisir entre 2 équipes aléatoires et 2 équipes prédéfinies.\n" +
                       "-Vous devez toujours ajouter au moins 2 joueurs pour commencer un match.\n" +
                       "-Assurez-vous de choisir l'équipe avant de sélectionner un joueur si vous le faites dans les équipes prédéfinies.\n" +
                       "-Lorsque vous complétez les équipes, ajoutez toujours une virgule entre chaque joueur.\n" +
                       "-Vous devez toujours remplir les champs de temps, vous pouvez les mettre à '0' si vous le souhaitez.\n" +
                        "-Appuyez sur l'icône du ballon de football pour ajouter un nouveau joueur.\n" +
                       "-Appuyez sur l'icône du ballon de football pour ajouter un but.");
            }
        });
    }

}
