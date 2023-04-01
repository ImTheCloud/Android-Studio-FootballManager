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

    // Rename the getter methods to avoid conflicts with EditText's getText() method
    public String getFameText() {
        return fame.getText().toString();
    }

    public String getWinText() {
        return win.getText().toString();
    }

    public String getLoseText() {
        return lose.getText().toString();
    }

    public String getTieText() {
        return tie.getText().toString();
    }

    public String getBonusText() {
        return bonus.getText().toString();
    }

    public String getYellowText() {
        return yellow.getText().toString();
    }

    public String getRankText() {
        return rank.getText().toString();
    }
}
