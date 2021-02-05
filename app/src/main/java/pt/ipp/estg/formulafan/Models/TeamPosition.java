package pt.ipp.estg.formulafan.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import java.io.Serializable;

@Entity(primaryKeys = {"position"}, indices = {@Index(value = {"position"}, unique = true)})
public class TeamPosition implements Serializable {

    public int position;
    public int points;
    public int wins;
    @Embedded
    public Team team;


    public TeamPosition(int position, Team team, int points, int wins) {
        this.position = position;
        this.team = team;
        this.points = points;
        this.wins = wins;
    }

}
