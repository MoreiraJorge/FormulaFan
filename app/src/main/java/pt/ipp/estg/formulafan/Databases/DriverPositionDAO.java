package pt.ipp.estg.formulafan.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.ipp.estg.formulafan.Models.DriverPosition;

@Dao
public interface DriverPositionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDriversPosition(DriverPosition... driversPositions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllDriversPositions(List<DriverPosition> driversPositions);

    @Query("Select * From DriverPosition")
    public LiveData<List<DriverPosition>> getDriversPositions();

    @Delete
    public void deleteDriversPosition(DriverPosition... driversPositions);
}
