package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.R;

public class QuizzHistoryRecyclerViewAdapter extends RecyclerView.Adapter<QuizzHistoryRecyclerViewAdapter.ViewHolder>{

    private List<QuizDone> quizzesDone;

    public QuizzHistoryRecyclerViewAdapter() {
        this.quizzesDone = temporaryQuizes(50);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

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
            quizTitle = view.findViewById(R.id.quizTitleView);
            quizPoints = view.findViewById(R.id.quizPointsView);
        }
    }

    /**
     * Objetos Quiz para testar a UI
     * (Apagar mais tarde)
     * @param numOfQuizes
     * @return
     */
    private List<QuizDone> temporaryQuizes(int numOfQuizes){
        List<QuizDone> temporaryList = new ArrayList<>();
        Random rd = new Random();

        for(int i = 0; i<numOfQuizes; i++){
            temporaryList.add(new QuizDone("Quiz" + i,rd.nextInt(1000)));
        }

        return temporaryList;
    }
}
