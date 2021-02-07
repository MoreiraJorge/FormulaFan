package pt.ipp.estg.formulafan.Databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import pt.ipp.estg.formulafan.Models.QuizDone;

@Dao
public interface QuizDoneDatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuizDone(QuizDone... quiz);
}
