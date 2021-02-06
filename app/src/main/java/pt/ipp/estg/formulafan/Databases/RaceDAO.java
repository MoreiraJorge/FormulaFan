package pt.ipp.estg.formulafan.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;

@Dao
public interface RaceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRace(Race... races);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllRaces(List<Race> races);

    @Query("Select * From Race")
    public LiveData<List<Race>> getRaces();

    @Query("Select * From Race")
    public List<Race> getStaticRaces();

    @Delete
    public void deleteRace(Race... races);

}
