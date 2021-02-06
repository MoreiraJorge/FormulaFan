package pt.ipp.estg.formulafan.Models;

import java.util.List;

public class Quiz {

    private String Title;
    private List<Question> questions;
    private boolean isDone;

    public Quiz(String title, List<Question> questions) {
        Title = title;
        this.questions = questions;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


}
