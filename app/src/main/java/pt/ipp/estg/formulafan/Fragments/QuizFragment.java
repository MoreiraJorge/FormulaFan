package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

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

import static pt.ipp.estg.formulafan.NativeServices.GeofenceBroadcastReceiver.CIRCUIT;

public class QuizFragment extends Fragment implements View.OnClickListener {

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
    private Context context;
    private View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quiz, container, false);

        Intent intent = getActivity().getIntent();
        circuitName = intent.getStringExtra(CIRCUIT);
        userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        questionTextView = view.findViewById(R.id.questionTextView);
        answerGroup = view.findViewById(R.id.radioGroup);
        answer1 = view.findViewById(R.id.resposta1);
        answer2 = view.findViewById(R.id.resposta2);
        answer3 = view.findViewById(R.id.resposta3);
        answer4 = view.findViewById(R.id.resposta4);
        confirm = view.findViewById(R.id.confirmButton);

        userInfoViewModel =
                new ViewModelProvider(this,
                        new ViewModelProvider.AndroidViewModelFactory((Application) getActivity().getApplicationContext())).get(UserInfoViewModel.class);

        quizDoneViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) getActivity().getApplicationContext())).get(QuizDoneViewModel.class);

        userInfoViewModel.getUserInfo(userMail).observe(this, (user) -> {
            if (user != null) {
                this.user = new User(user.email, user.userName);
                this.user.qi = user.qi;
                this.user.quizesDone = user.quizesDone;
                this.user.correctAnswers = user.correctAnswers;
                this.user.wrongAnsers = user.wrongAnsers;
            }
        });

        if (circuitName != null) {
            pastRaceViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) getActivity().getApplicationContext())).get(PastRaceViewModel.class);

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
                                Toast.makeText(context, this.getString(R.string.no_available_quiz),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (answer1.isChecked() || answer2.isChecked() ||
                answer3.isChecked() || answer4.isChecked()) {
            checkAnswer();
        } else {
            Toast.makeText(context, this.getString(R.string.select_an_option),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer() {
        RadioButton rbSelected = view.findViewById(answerGroup.getCheckedRadioButtonId());

        int answerNr = answerGroup.indexOfChild(rbSelected) + 1;

        boolean isCorrect;

        if (isCorrect = currentQuestion.checkAnswer(answerNr)) {
            userScore += currentQuestion.points;
            if (this.user != null) {
                this.user.correctAnswers = this.user.correctAnswers + 1;
            }

        } else {
            if (this.user != null) {
                this.user.wrongAnsers = this.user.wrongAnsers + 1;
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
            this.user.qi = this.user.qi + userScore;
            this.user.quizesDone = this.user.quizesDone + 1;
            userInfoViewModel.updateUserInfo(userMail, this.user);
        }

        getActivity().finish();
    }
}