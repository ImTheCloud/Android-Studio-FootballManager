package Compo;

import android.widget.EditText;
public class CompoSave {
    EditText player1,player2,player3,player4,player5,player6,player7,player8,player9,player10,player11;

    public CompoSave(EditText player1, EditText player2, EditText player3, EditText player4, EditText player5, EditText player6, EditText player7, EditText player8, EditText player9, EditText player10, EditText player11) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.player5 = player5;
        this.player6 = player6;
        this.player7 = player7;
        this.player8 = player8;
        this.player9 = player9;
        this.player10 = player10;
        this.player11 = player11;
    }

    public String getPlayer1() {
        return player1.getText().toString();
    }

    public String getPlayer2() {
        return player2.getText().toString();
    }

    public String getPlayer3() {
        return player3.getText().toString();
    }

    public String getPlayer4() {
        return player4.getText().toString();
    }

    public String getPlayer5() {
        return player5.getText().toString();
    }

    public String getPlayer6() {
        return player6.getText().toString();
    }

    public String getPlayer7() {
        return player7.getText().toString();
    }

    public String getPlayer8() {
        return player8.getText().toString();
    }

    public String getPlayer9() {
        return player9.getText().toString();
    }

    public String getPlayer10() {
        return player10.getText().toString();
    }

    public String getPlayer11() {
        return player11.getText().toString();
    }



}
