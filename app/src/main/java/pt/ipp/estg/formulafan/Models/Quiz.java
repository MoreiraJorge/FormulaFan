package pt.ipp.estg.formulafan.Models;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {

    public String Title;
    public List<Question> questions;

    public Quiz(String title, List<Question> questions) {
        Title = title;
        this.questions = questions;
    }
}
