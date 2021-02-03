package pt.ipp.estg.formulafan.Models;

import java.io.Serializable;

public class Result implements Serializable {

    public int position;
    public String driverName;
    public String teamName;

    public Result(int position, String driverName, String teamName) {
        this.position = position;
        this.driverName = driverName;
        this.teamName = teamName;
    }
}
