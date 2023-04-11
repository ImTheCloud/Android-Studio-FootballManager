package Statistics;

import android.widget.EditText;
import android.widget.Spinner;

public class StatisticsSave {
    EditText fame,win,lose,tie,bonus,yellow,rank;
    Spinner position;

    public StatisticsSave(EditText etFame, EditText etWin, EditText etLose, EditText etTie, EditText et5Goal, EditText etYellowCard, EditText etRank, Spinner position) {
        this.fame = etFame;
        this.win = etWin;
        this.lose = etLose;
        this.tie = etTie;
        this.bonus = et5Goal;
        this.yellow = etYellowCard;
        this.rank = etRank;
        this.position = position;
    }

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

    public String getPosition() {
        return position.getSelectedItem().toString();
    }
}
