package pt.ipp.estg.formulafan.Models;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(primaryKeys = {"constructorId"}, indices = {@Index(value = {"constructorId"}, unique = true)})
public class Team {

    public String constructorId;
    public String name;
    public String teamNationality;

    public Team(String constructorId, String name, String teamNationality) {
        this.constructorId = constructorId;
        this.name = name;
        this.teamNationality = teamNationality;
    }
}
