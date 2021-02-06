package pt.ipp.estg.formulafan.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.ipp.estg.formulafan.Models.Question;
import pt.ipp.estg.formulafan.Models.Quiz;
import pt.ipp.estg.formulafan.R;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private Button confirm;
    private RadioGroup answerGroup;

    private Question currentQuestion;
    private Quiz quiz;
    private List<Question> questionList;
    private int questionCounter = 0;
    private int userScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        answerGroup = findViewById(R.id.radioGroup);
        answer1 = findViewById(R.id.resposta1);
        answer2 = findViewById(R.id.resposta2);
        answer3 = findViewById(R.id.resposta3);
        answer4 = findViewById(R.id.resposta4);
        confirm = findViewById(R.id.confirmButton);

        questionList = temporaryQuestions(10);
        quiz = new Quiz("Test Quiz", questionList);

        showNextQuestion();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1.isChecked() || answer2.isChecked() ||
                        answer3.isChecked() || answer4.isChecked()) {
                    checkAnswer();
                } else {
                    Toast.makeText(QuizActivity.this, "Please select an answer",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void checkAnswer() {
        RadioButton rbSelected = findViewById(answerGroup.getCheckedRadioButtonId());
        int answerNr = answerGroup.indexOfChild(rbSelected) + 1;
        if (currentQuestion.checkAnswer(answerNr)) {
            userScore += currentQuestion.points;
            //criar aqui objeto AnsweredQuestion para guardar resposta do user e se esta certo ou errado
            //adicionar objeto a lista de answeredQuestions de quizdone
        }

        showNextQuestion();
    }

    private void showNextQuestion() {
        answerGroup.clearCheck();
        if (questionCounter < questionList.size()) {

            if (questionCounter == questionList.size() - 1) {
                confirm.setText("Finalizar");
            }

            currentQuestion = questionList.get(questionCounter);
            questionTextView.setText(currentQuestion.title);
            answer1.setText(currentQuestion.option1);
            answer2.setText(currentQuestion.option2);
            answer3.setText(currentQuestion.option3);
            answer4.setText(currentQuestion.option4);
            questionCounter++;
        } else {
            finishQuiz();
        }
    }


    private void finishQuiz() {
        quiz.setDone(true);
        //Aqui adicionar quizDone com AnsweredQuestions e na BD para aparecer no historico
        finish();
    }

    /**
     * Objetos Question temporario
     * para testar UI
     *
     * @param numOfQuestions
     * @return
     */
    private List<Question> temporaryQuestions(int numOfQuestions) {
        List<Question> temporaryList = new ArrayList<>();
        Random rd = new Random();
        for (int i = 0; i < numOfQuestions; i++) {
            temporaryList.add(new Question("Questão " + i, 2, rd.nextInt(10),
                    "errada", "resposta", "errada", "errada"));
        }

        return temporaryList;
    }
}
