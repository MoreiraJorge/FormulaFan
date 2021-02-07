package pt.ipp.estg.formulafan.Models;

import java.util.List;

public class Quiz {

    public String Title;
    public List<Question> questions;
    public boolean isDone;

    public Quiz(String title, List<Question> questions) {
        Title = title;
        this.questions = questions;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


}
