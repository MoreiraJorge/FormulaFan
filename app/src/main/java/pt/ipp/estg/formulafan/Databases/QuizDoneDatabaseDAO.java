package pt.ipp.estg.formulafan.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.ipp.estg.formulafan.Models.QuizDone;

@Dao
public interface QuizDoneDatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuizDone(QuizDone... quiz);

    @Query("Select * From QuizDone Where author = :user_mail")
    LiveData<List<QuizDone>> getQuizesDoneFromUser(String user_mail);
}
