package pt.ipp.estg.formulafan.Models;

import java.io.Serializable;

public class Question implements Serializable {

    public String title;
    public int answer;
    public int points;
    public String option1;
    public String option2;
    public String option3;
    public String option4;

    public Question(String title, int answer, int points,
                    String option1,
                    String option2,
                    String option3,
                    String option4) {

        this.title = title;
        this.answer = answer;
        this.points = points;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public boolean checkAnswer(int userAnswer) {
        return userAnswer == this.answer ? true : false;
    }


}
