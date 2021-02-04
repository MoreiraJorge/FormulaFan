package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.ipp.estg.formulafan.Interfaces.IQuizHistoryListener;
import pt.ipp.estg.formulafan.Models.QuestionAnswered;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.R;

public class QuizzHistoryRecyclerViewAdapter extends RecyclerView.Adapter<QuizzHistoryRecyclerViewAdapter.ViewHolder> {

    private List<QuizDone> quizzesDone;
    private IQuizHistoryListener quizHistoryListener;

    public QuizzHistoryRecyclerViewAdapter(Context context) {
        this.quizzesDone = temporaryQuizes(50);
        this.quizHistoryListener = (IQuizHistoryListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View quizzHistoryView = inflater
                .inflate(R.layout.fragment_quizz_history_item, parent, false);

        return new ViewHolder(quizzHistoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizDone quizDone = quizzesDone.get(position);
        holder.quizTitle.setText(quizDone.title);
        holder.quizPoints.setText(String.valueOf(quizDone.score));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizHistoryListener.showDoneQuizDetails(quizDone);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzesDone.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView quizTitle;
        public TextView quizPoints;

        public ViewHolder(View view) {
            super(view);
            quizTitle = view.findViewById(R.id.questionTitle);
            quizPoints = view.findViewById(R.id.correctWrongView);
        }
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
