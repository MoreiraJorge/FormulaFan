package pt.ipp.estg.formulafan.Activities;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.formulafan.Models.Question;
import pt.ipp.estg.formulafan.Models.QuestionAnswered;
import pt.ipp.estg.formulafan.Models.Quiz;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Models.User;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.PastRaceViewModel;
import pt.ipp.estg.formulafan.ViewModels.QuizDoneViewModel;
import pt.ipp.estg.formulafan.ViewModels.UserInfoViewModel;

import static pt.ipp.estg.formulafan.NativeServices.GeofenceBroadcastReceiver.CLOSEST_CIRCUIT;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private Button confirm;
    private RadioGroup answerGroup;

    private QuizDoneViewModel quizDoneViewModel;
    private PastRaceViewModel pastRaceViewModel;
    private UserInfoViewModel userInfoViewModel;
    private User user;
    private String userMail;
    private List<Question> questionList;
    private List<QuestionAnswered> answeredQuestions;
    private List<Race> pastRaceList;
    private int questionCounter;
    private Question currentQuestion;
    private String circuitName;
    private int userScore;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = this.getIntent();
        circuitName = intent.getStringExtra(CLOSEST_CIRCUIT);
        userMail = getIntent().getExtras().getString("MAIL");

        questionTextView = findViewById(R.id.questionTextView);
        answerGroup = findViewById(R.id.radioGroup);
        answer1 = findViewById(R.id.resposta1);
        answer2 = findViewById(R.id.resposta2);
        answer3 = findViewById(R.id.resposta3);
        answer4 = findViewById(R.id.resposta4);
        confirm = findViewById(R.id.confirmButton);

        userInfoViewModel =
                new ViewModelProvider(this,
                        new ViewModelProvider.AndroidViewModelFactory((Application) getApplicationContext())).get(UserInfoViewModel.class);

        quizDoneViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) getApplicationContext())).get(QuizDoneViewModel.class);

        userInfoViewModel.getUserInfo(userMail).observe(this, (user) -> {
            if (user != null) {
                this.user = new User(user.email, user.userName);
                this.user.setQi(user.qi);
                this.user.setQuizesDone(user.quizesDone);
                this.user.setCorrectAnswers(user.correctAnswers);
                this.user.setWrongAnsers(user.wrongAnsers);
            }
        });

        if (circuitName != null) {
            pastRaceViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) this.getApplicationContext())).get(PastRaceViewModel.class);

            pastRaceViewModel.getAllRaces().observe(this, (races) -> {
                        if (races.size() != 0) {
                            this.pastRaceList = races;

                            questionList = generateQuestions();
                            quiz = new Quiz(" " + circuitName + " - Quiz", questionList);
                            questionCounter = 0;
                            userScore = 0;
                            answeredQuestions = new ArrayList<>();

                            if (questionList.size() != 0) {
                                showNextQuestion();
                                confirm.setOnClickListener(this);
                            } else {
                                Toast.makeText(this, this.getString(R.string.no_available_quiz),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void onClick(View v) {
        if (answer1.isChecked() || answer2.isChecked() ||
                answer3.isChecked() || answer4.isChecked()) {
            checkAnswer();
        } else {
            Toast.makeText(QuizActivity.this, this.getString(R.string.select_an_option),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void checkAnswer() {
        RadioButton rbSelected = findViewById(answerGroup.getCheckedRadioButtonId());
        int answerNr = answerGroup.indexOfChild(rbSelected) + 1;
        boolean isCorrect;

        if (isCorrect = currentQuestion.checkAnswer(answerNr)) {
            userScore += currentQuestion.points;
            if (this.user != null) {
                this.user.setCorrectAnswers(this.user.correctAnswers + 1);
            }

        } else {
            if (this.user != null) {
                this.user.setWrongAnsers(this.user.wrongAnsers + 1);
            }
        }

        answeredQuestions.add(new QuestionAnswered(currentQuestion.title, rbSelected.getText().toString(), isCorrect));
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

    private List<Question> generateQuestions() {
        List<Question> questionList = new ArrayList<>();
        int selectedRace = -1;

        for (int i = 0; i < pastRaceList.size(); i++) {
            if (pastRaceList.get(i).circuit.circuitName.equals(circuitName)) {
                selectedRace = i;
                break;
            }
        }

        if (selectedRace != -1) {

            //Questão temporada
            questionList.add(new Question("Qual foi a ultima season que passou no circuito " + circuitName + " ?", 3, 2,
                    "" + (pastRaceList.get(selectedRace).season - 2),
                    "" + (pastRaceList.get(selectedRace).season - 1),
                    "" + pastRaceList.get(selectedRace).season,
                    "" + (pastRaceList.get(selectedRace).season - 3)));

            //Questão ronda
            questionList.add(new Question("Qual foi a ultima ronda que passou no circuito " + circuitName + " ?", 4, 3,
                    "" + (pastRaceList.get(selectedRace).round + 2),
                    "" + (pastRaceList.get(selectedRace).round + 1),
                    "" + (pastRaceList.get(selectedRace).round + 3),
                    "" + pastRaceList.get(selectedRace).round));

            //Questão data
            if (selectedRace + 3 < pastRaceList.size()) {
                questionList.add(new Question("Quando ocorreu a ultima corrida no circuito " + circuitName + " ?", 2, 4,
                        pastRaceList.get(selectedRace + 1).date.toGMTString(),
                        pastRaceList.get(selectedRace).date.toGMTString(),
                        pastRaceList.get(selectedRace + 2).date.toGMTString(),
                        pastRaceList.get(selectedRace + 3).date.toGMTString()));
            } else {
                questionList.add(new Question("Quando ocorreu a ultima corrida no circuito " + circuitName + " ?", 2, 4,
                        pastRaceList.get(selectedRace - 1).date.toGMTString(),
                        pastRaceList.get(selectedRace).date.toGMTString(),
                        pastRaceList.get(selectedRace - 2).date.toGMTString(),
                        pastRaceList.get(selectedRace - 3).date.toGMTString()));
            }
        }
        return questionList;
    }

    private void finishQuiz() {
        QuizDone quizDone = new QuizDone(userMail, quiz.Title, userScore, answeredQuestions);
        quizDoneViewModel.insertQuiz(quizDone);

        if (this.user != null) {
            this.user.setQi(this.user.qi + userScore);
            this.user.setQuizesDone(this.user.quizesDone + 1);
            userInfoViewModel.updateUserInfo(userMail, this.user);
        }

        finish();
    }

}
