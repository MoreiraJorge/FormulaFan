package pt.ipp.estg.formulafan.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(primaryKeys = {"season", "round"}, indices = {@Index(value = {"season", "round"}, unique = true)})
@TypeConverters({Converters.class})
public class Race implements Serializable {

    public int season;
    public int round;
    public String raceName;
    @Embedded
    public Circuit circuit;
    public Date date;
    //Completar Time
    //public Time time;

    public Race(int season, int round, String raceName, Circuit circuit, Date date) {
        this.season = season;
        this.round = round;
        this.raceName = raceName;
        this.circuit = circuit;
        this.date = date;
    }
}
