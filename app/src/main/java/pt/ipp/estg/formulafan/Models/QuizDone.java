package pt.ipp.estg.formulafan.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.List;

@Entity(indices = {@Index(value = {"quizDoneId"}, unique = true)})
public class QuizDone implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int quizDoneId;
    public String author;
    public String title;
    public int score;
    @TypeConverters({Converters.class})
    public List<QuestionAnswered> answeredQuestions;

    public QuizDone(String author, String title, int score, List<QuestionAnswered> answeredQuestions) {
        this.author = author;
        this.title = title;
        this.score = score;
        this.answeredQuestions = answeredQuestions;
    }
}
