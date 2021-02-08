package pt.ipp.estg.formulafan.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"userId"}, unique = true)})
public class User implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int userId;
    public String email;
    public String userName;
    public int qi;
    public int correctAnswers;
    public int wrongAnsers;
    public int quizesDone;
    public int quizesMissed;

    public User(String email, String userName) {
        this.email = email;
        this.userName = userName;
        this.correctAnswers = 0;
        this.qi = 0;
        this.wrongAnsers = 0;
        this.quizesDone = 0;
        this.quizesMissed = 0;
    }

    @Ignore
    public User(String email, String userName, int qi, int correctAnswers, int wrongAnsers,
                int quizesDone, int quizesMissed) {
        this.email = email;
        this.userName = userName;
        this.qi = qi;
        this.correctAnswers = correctAnswers;
        this.wrongAnsers = wrongAnsers;
        this.quizesDone = quizesDone;
        this.quizesMissed = quizesMissed;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setWrongAnsers(int wrongAnsers) {
        this.wrongAnsers = wrongAnsers;
    }

    public void setQuizesDone(int quizesDone) {
        this.quizesDone = quizesDone;
    }

    public void setQuizesMissed(int quizesMissed) {
        this.quizesMissed = quizesMissed;
    }

    public void setQi(int qi) {
        this.qi = qi;
    }
}
