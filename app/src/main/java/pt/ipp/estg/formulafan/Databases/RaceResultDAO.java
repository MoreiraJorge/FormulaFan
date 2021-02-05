package pt.ipp.estg.formulafan.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.ipp.estg.formulafan.Models.RaceResult;

@Dao
public interface RaceResultDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRaceResult(RaceResult... raceResults);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllRacesResults(List<RaceResult> raceResults);

    @Query("Select * From RaceResult")
    public LiveData<List<RaceResult>> getRacesResults();

    @Delete
    public void deleteRaceResult(RaceResult... raceResults);
}
