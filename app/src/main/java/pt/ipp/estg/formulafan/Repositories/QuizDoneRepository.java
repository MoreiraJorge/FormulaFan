package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Databases.QuizDoneDatabase;
import pt.ipp.estg.formulafan.Databases.QuizDoneDatabaseDAO;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.Models.Race;

public class QuizDoneRepository {
    private QuizDoneDatabaseDAO quizDoneDao;

    public QuizDoneRepository(Application application) {
        QuizDoneDatabase db = QuizDoneDatabase.getDatabase(application);
        quizDoneDao = db.getQuizDoneDAO();
    }

    public void insertQuizDone(QuizDone quiz) {
        QuizDoneDatabase.databaseWriteExecutor.execute(() -> {
            quizDoneDao.insertQuizDone(quiz);
        });
    }

    public LiveData<List<QuizDone>> getQuizesDone(String email) {
        return quizDoneDao.getQuizesDoneFromUser(email);
    }
}
