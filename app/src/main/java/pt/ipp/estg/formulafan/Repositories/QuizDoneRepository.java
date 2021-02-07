package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;

import pt.ipp.estg.formulafan.Databases.QuizDoneDatabase;
import pt.ipp.estg.formulafan.Databases.QuizDoneDatabaseDAO;
import pt.ipp.estg.formulafan.Models.QuizDone;

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
}
