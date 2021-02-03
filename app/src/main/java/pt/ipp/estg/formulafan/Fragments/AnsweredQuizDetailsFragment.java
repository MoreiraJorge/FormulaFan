package pt.ipp.estg.formulafan.Fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;

public class AnsweredQuizDetailsFragment extends Fragment {

    private QuizDone quizDone;
    private ConstraintLayout messageView;
    private RecyclerView recyclerView;

    public AnsweredQuizDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle quizBundle = this.getArguments();
        if (quizBundle != null) {
            quizDone = (QuizDone) quizBundle.getSerializable(FormulaFanMainActivity.SELECTED_QUIZ_DONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_answered_quiz_details, container, false);

        messageView = view.findViewById(R.id.messageViewQuizDetail);
        recyclerView = view.findViewById(R.id.quizAnswers);

        if (quizDone != null) {
            messageView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void updateQuiz(QuizDone quiz) {

    }
}