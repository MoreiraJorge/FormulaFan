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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        View view = inflater.inflate(R.layout.fragment_quizz_history, container, false);

        recyclerView = view.findViewById(R.id.quizzHistoryList);
        quizzHistoryRecyclerViewAdapter =
                new QuizzHistoryRecyclerViewAdapter();

        recyclerView.setAdapter(quizzHistoryRecyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}