package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.ipp.estg.formulafan.Models.QuestionAnswered;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.R;

public class QuizzHistoryFragment extends Fragment {

    private Context context;
    private QuizzHistoryRecyclerViewAdapter quizzHistoryRecyclerViewAdapter;
    private RecyclerView recyclerView;

    public QuizzHistoryFragment() {
    }

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
        View view = inflater.inflate(R.layout.fragment_quiz_history, container, false);

        recyclerView = view.findViewById(R.id.quizzHistoryList);
        quizzHistoryRecyclerViewAdapter =
                new QuizzHistoryRecyclerViewAdapter(context, temporaryQuizes(50));
        recyclerView.setAdapter(quizzHistoryRecyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    /**
     * Objetos Quiz para testar a UI
     * (Apagar mais tarde)
     *
     * @param numOfQuizes
     * @return
     */
    private List<QuizDone> temporaryQuizes(int numOfQuizes) {
        List<QuizDone> temporaryList = new ArrayList<>();
        Random rd = new Random();

        for (int i = 0; i < numOfQuizes; i++) {
            temporaryList.add(new QuizDone("Quiz" + i, rd.nextInt(1000),
                    temporaryQuestions(10)));
        }

        return temporaryList;
    }

    /**
     * Objetos QuestionAnswered temporario
     * para testar UI
     *
     * @param numOfQuestions
     * @return
     */
    private List<QuestionAnswered> temporaryQuestions(int numOfQuestions) {
        List<QuestionAnswered> temporaryList = new ArrayList<>();
        Random rd = new Random();
        for (int i = 0; i < numOfQuestions; i++) {
            temporaryList.add(new QuestionAnswered("Questao" + i, "Minha resposta",
                    rd.nextBoolean()));
        }

        return temporaryList;
    }
}