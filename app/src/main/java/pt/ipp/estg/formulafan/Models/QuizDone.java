package pt.ipp.estg.formulafan.Models;

import java.io.Serializable;
import java.util.List;

public class QuizDone implements Serializable {

    public String title;
    public int score;
    public List<QuestionAnswered> answeredQuestions;

    public QuizDone(String title, int score, List<QuestionAnswered> answeredQuestions) {
        this.title = title;
        this.score = score;
        this.answeredQuestions = answeredQuestions;
    }
}
