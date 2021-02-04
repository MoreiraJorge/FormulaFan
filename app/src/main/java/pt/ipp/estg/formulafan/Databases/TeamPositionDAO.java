package pt.ipp.estg.formulafan.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.ipp.estg.formulafan.Models.TeamPosition;

@Dao
public interface TeamPositionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTeamsPosition(TeamPosition... teamsPositions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllTeamsPositions(List<TeamPosition> teamsPositions);

    @Query("Select * From TeamPosition")
    public LiveData<List<TeamPosition>> getTeamsPositions();

    @Delete
    public void deleteTeamsPosition(TeamPosition... teamsPositions);

}
