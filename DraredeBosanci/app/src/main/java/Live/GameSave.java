package Live;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class GameSave {
    LatLng userLocation;
    String goalTeam1,goalTeam2;
    String timeFirstHalf,timeSecondHalf,half;
    String mail;
    String date;
    List<String> team2,team1;

    public GameSave(LatLng userLocation, String goalTeam1, String goalTeam2, String timerF, String timerS, String timerHF, String mail, String date, List<String> team2, List<String> team1) {
        this.userLocation = userLocation;
        this.goalTeam1 = goalTeam1;
        this.goalTeam2 = goalTeam2;
        this.timeFirstHalf = timerF;
        this.timeSecondHalf = timerS;
        this.half = timerHF;
        this.mail = mail;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getGoalTeam1() {
        return goalTeam1;
    }
    public String getGoalTeam2() {
        return goalTeam2;
    }
    public String getHalf() {
        return half;
    }
    public String getTimeSecondHalf() {
        return timeSecondHalf;
    }
    public String getTimeFirstHalf() {
        return timeFirstHalf;
    }
    public List<String> getTeam2() {
        return team2;
    }
    public List<String> getTeam1() {
        return team1;
    }
    public LatLng getUserLocation() {
        return userLocation;
    }
    public String getData() {
        return date;
    }
    public String getEmail() {
        return mail;
    }

}
