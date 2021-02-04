package pt.ipp.estg.formulafan.Models;

import java.io.Serializable;

public class QuestionAnswered implements Serializable {

    public String title;
    public String answer;
    public String correct;

    public QuestionAnswered(String title, String answer, boolean isCorrect) {
        this.title = title;
        this.answer = answer;
        setCorrect(isCorrect);
    }

    public void setCorrect(boolean isCorrect) {
        correct = (isCorrect) ? "Correto!" : "Errado!";
    }
}
