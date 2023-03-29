package Firebase;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Game {

    LatLng userLocation;
    String goalTeam1,goalTeam2;
    String timeFirstHalf,timeSecondHalf,half;


    public FirebaseAuth getMail() {
        return mail;
    }

    FirebaseAuth mail;

    String date;
    List<String> team2,team1;

    public Game(FirebaseAuth mail) {
        this.mail=mail;
    }


    public Game(String goalTeam1,String goalTeam2) {
        this.goalTeam1 = goalTeam1;
        this.goalTeam2 = goalTeam2;
    }


    public Game(String timerHF, String timerF, String timerS) {
        this.half = timerF;
        this.timeSecondHalf = timerS;
        this.timeFirstHalf = timerHF;
    }

    public Game(List<String> team1, List<String> team2) {
        this.team1=team1;
        this.team2=team2;
    }

    public Game(LatLng userLocation) {
        this.userLocation=userLocation;
    }

    public Game(String date) {
        this.date=date;
    }


    public String getGoalTeam1() {
        return goalTeam1;
    }
    public String getGoalTeam2() {
        return goalTeam2;
    }
    public void setGoalTeam2(String goalTeam2) {
        this.goalTeam2 = goalTeam2;
    }
    public void setGoalTeam1(String goalTeam1) {
        this.goalTeam1 = goalTeam1;
    }

    public String getHalf() {
        return half;
    }

    public void setHalf(String half) {
        this.half = half;
    }

    public String getTimeSecondHalf() {
        return timeSecondHalf;
    }

    public void setTimeSecondHalf(String timeSecondHalf) {
        this.timeSecondHalf = timeSecondHalf;
    }
    public String getTimeFirstHalf() {
        return timeFirstHalf;
    }

    public void setTimeFirstHalf(String timeFirstHalf) {
        this.timeFirstHalf = timeFirstHalf;
    }

    public List<String> getTeam2() {
        return team2;
    }

    public void setTeam2(List<String> team2) {
        this.team2 = team2;
    }

    public List<String> getTeam1() {
        return team1;
    }

    public void setTeam1(List<String> team1) {
        this.team1 = team1;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }
    public void setUserLocation(LatLng userLocation) {
        this.userLocation = userLocation;
    }

    public String getData() {
        return date;
    }


}
