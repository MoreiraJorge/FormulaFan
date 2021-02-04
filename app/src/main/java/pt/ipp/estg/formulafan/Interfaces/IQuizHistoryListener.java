package pt.ipp.estg.formulafan.Interfaces;

import androidx.fragment.app.FragmentManager;

import pt.ipp.estg.formulafan.Models.QuizDone;

public interface IQuizHistoryListener {
    void changeToQuizHistory();
    void showDoneQuizDetails(QuizDone quiz);
}
