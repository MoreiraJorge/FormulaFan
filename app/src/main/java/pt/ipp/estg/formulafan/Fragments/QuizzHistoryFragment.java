package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.QuizDoneViewModel;

public class QuizzHistoryFragment extends Fragment {

    private Context context;
    private QuizzHistoryRecyclerViewAdapter quizzHistoryRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private QuizDoneViewModel quizDoneViewModel;

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

        String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        quizDoneViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory((Application) getActivity()
                        .getApplicationContext())).get(QuizDoneViewModel.class);


        recyclerView = view.findViewById(R.id.quizzHistoryList);
        quizzHistoryRecyclerViewAdapter =
                new QuizzHistoryRecyclerViewAdapter(context);
        recyclerView.setAdapter(quizzHistoryRecyclerViewAdapter);

        quizDoneViewModel.getQuizesDone(mail).observe(getViewLifecycleOwner(), (quizes) -> {
                    quizzHistoryRecyclerViewAdapter.setQuizzesDone(quizes);
                    quizzHistoryRecyclerViewAdapter.notifyDataSetChanged();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}