package pt.ipp.estg.formulafan.ViewModels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.Repositories.QuizDoneRepository;

public class QuizDoneViewModel extends AndroidViewModel {
    private final QuizDoneRepository quizDoneRepository;

    public QuizDoneViewModel(@NonNull Application application) {
        super(application);
        quizDoneRepository = new QuizDoneRepository(application);
    }

    public void insertQuiz(QuizDone quizDone) {
        quizDoneRepository.insertQuizDone(quizDone);
    }

    public LiveData<List<QuizDone>> getQuizesDone(String email) {
        return quizDoneRepository.getQuizesDone(email);
    }
}
