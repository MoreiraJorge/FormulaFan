package pt.ipp.estg.formulafan.Models;

import java.io.Serializable;

public class QuizDone implements Serializable, Comparable<QuizDone> {
    public String title;
    public int score;

    public QuizDone(String title, int score) {
        this.title = title;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(QuizDone o) {
        if(o.getScore() > this.score){
            return 1;
        } else {
            return 0;
        }
    }
}
