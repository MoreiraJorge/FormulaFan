package pt.ipp.estg.formulafan.Models;

import androidx.room.Embedded;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(primaryKeys = {"circuitId"})
public class Circuit implements Serializable {

    public String circuitId;
    public String circuitName;
    @Embedded
    public Location location;

    public Circuit(String circuitId, String circuitName, Location location) {
        this.circuitId = circuitId;
        this.circuitName = circuitName;
        this.location = location;
    }
}
