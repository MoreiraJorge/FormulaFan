package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.QuestionAnswered;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.R;

public class AnsweredQuizDetailsFragment extends Fragment {

    private Context context;
    private QuizDone quizDone;
    private ConstraintLayout messageView;
    private RecyclerView recyclerView;
    private AnsweredQuizDetailsFragmentAdapter answeredQuizDetailsFragmentAdapter;
    private LinearLayout viewAnswers;

    public AnsweredQuizDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle quizBundle = this.getArguments();
        if (quizBundle != null) {
            quizDone = (QuizDone) quizBundle.getSerializable(FormulaFanMainActivity.SELECTED_QUIZ_DONE);
            System.out.println("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_answered_quiz_details, container, false);

        messageView = view.findViewById(R.id.messageViewQuizDetail);
        recyclerView = (RecyclerView) view.findViewById(R.id.quizAnswers);
        viewAnswers = view.findViewById(R.id.quizAnswersView);

        answeredQuizDetailsFragmentAdapter =
                new AnsweredQuizDetailsFragmentAdapter(new ArrayList<QuestionAnswered>());
        recyclerView.setAdapter(answeredQuizDetailsFragmentAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        if (quizDone != null) {
            messageView.setVisibility(View.GONE);
            viewAnswers.setVisibility(View.VISIBLE);
            answeredQuizDetailsFragmentAdapter.setAnsweredQuestions(quizDone.answeredQuestions);
            answeredQuizDetailsFragmentAdapter.notifyDataSetChanged();
        }

        return view;
    }

    public void updateQuiz(QuizDone quiz) {
        this.quizDone = quiz;
        messageView.setVisibility(View.GONE);
        viewAnswers.setVisibility(View.VISIBLE);
        answeredQuizDetailsFragmentAdapter.setAnsweredQuestions(quizDone.answeredQuestions);
        answeredQuizDetailsFragmentAdapter.notifyDataSetChanged();
    }
}