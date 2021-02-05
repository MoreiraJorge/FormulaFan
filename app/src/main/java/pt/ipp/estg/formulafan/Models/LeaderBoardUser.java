package pt.ipp.estg.formulafan.Models;

import java.io.Serializable;

public class LeaderBoardUser implements Serializable {

    public String UserName;
    public int Qi;

    public LeaderBoardUser(String userName, int qi) {
        UserName = userName;
        Qi = qi;
    }
}
