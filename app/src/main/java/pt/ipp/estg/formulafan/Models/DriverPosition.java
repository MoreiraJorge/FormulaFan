package pt.ipp.estg.formulafan.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import java.io.Serializable;

@Entity(primaryKeys = {"position"}, indices = {@Index(value = {"position"}, unique = true)})
public class DriverPosition implements Serializable {

    public int position;
    @Embedded
    public Driver driver;
    @Embedded
    public Team team;
    public int points;
    public int wins;

    public DriverPosition(int position, Driver driver, Team team, int points, int wins) {
        this.position = position;
        this.driver = driver;
        this.team = team;
        this.points = points;
        this.wins = wins;
    }

}
