package pt.ipp.estg.formulafan.Interfaces;

import pt.ipp.estg.formulafan.Models.QuizDone;

public interface IQuizHistoryListener {
    void changeToQuizHistory();

    void showDoneQuizDetails(QuizDone quiz);
}
