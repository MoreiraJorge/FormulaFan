package pt.ipp.estg.formulafan.Models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.List;

@Entity(primaryKeys = {"season", "round"}, indices = {@Index(value = {"season", "round"}, unique = true)})
public class RaceResult implements Serializable {

    public int season;
    public int round;
    public String raceName;
    @TypeConverters({Converters.class})
    public List<Result> results;

    public RaceResult(int season, int round, String raceName, List<Result> results) {
        this.season = season;
        this.round = round;
        this.raceName = raceName;
        this.results = results;
    }

}
