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

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.R;

public class AnsweredQuizDetailsFragment extends Fragment {

    private Context context;
    private QuizDone quizDone;
    private ConstraintLayout messageView;
    private RecyclerView recyclerView;
    private AnsweredQuizDetailsFragmentAdapter answeredQuizDetailsFragmentAdapter;

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
        recyclerView = view.findViewById(R.id.quizAnswers);

        if (quizDone != null) {
            messageView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            answeredQuizDetailsFragmentAdapter =
                    new AnsweredQuizDetailsFragmentAdapter(quizDone.answeredQuestions);
            recyclerView.setAdapter(answeredQuizDetailsFragmentAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
        }

        return view;
    }

    public void updateQuiz(QuizDone quiz) {
        this.quizDone = quiz;
        messageView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        answeredQuizDetailsFragmentAdapter =
                new AnsweredQuizDetailsFragmentAdapter(quizDone.answeredQuestions);
        recyclerView.setAdapter(answeredQuizDetailsFragmentAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }
}