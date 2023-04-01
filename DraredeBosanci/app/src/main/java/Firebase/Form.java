package Firebase;

import android.widget.EditText;

public class Form {
    EditText fame,win,lose,tie,bonus,yellow,rank;

    public Form(EditText etFame, EditText etWin, EditText etLose, EditText etTie, EditText et5Goal, EditText etYellowCard, EditText etRank) {
        this.fame = etFame;
        this.win = etWin;
        this.lose = etLose;
        this.tie = etTie;
        this.bonus = et5Goal;
        this.yellow = etYellowCard;
        this.rank = etRank;
    }

    public EditText getFame() {
        return fame;
    }

    public EditText getWin() {
        return win;
    }

    public EditText getLose() {
        return lose;
    }

    public EditText getTie() {
        return tie;
    }

    public EditText getBonus() {
        return bonus;
    }

    public EditText getYellow() {
        return yellow;
    }

    public EditText getRank() {
        return rank;
    }

    // Rename this getter method to avoid conflict with EditText's getText() method
    public String getFameText() {
        return fame.getText().toString();
    }
}
