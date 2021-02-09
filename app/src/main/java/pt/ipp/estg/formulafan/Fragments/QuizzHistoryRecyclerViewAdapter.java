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

import pt.ipp.estg.formulafan.Interfaces.IQuizHistoryListener;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.R;

public class QuizzHistoryRecyclerViewAdapter extends RecyclerView.Adapter<QuizzHistoryRecyclerViewAdapter.ViewHolder> {

    private List<QuizDone> quizzesDone;
    private IQuizHistoryListener quizHistoryListener;

    public QuizzHistoryRecyclerViewAdapter(Context context) {
        this.quizzesDone = new ArrayList<>();
        this.quizHistoryListener = (IQuizHistoryListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View quizzHistoryView = inflater
                .inflate(R.layout.fragment_quiz_history_item, parent, false);

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

    public void setQuizzesDone(List<QuizDone> quizzesDone) {
        this.quizzesDone = quizzesDone;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView quizTitle;
        public TextView quizPoints;

        public ViewHolder(View view) {
            super(view);
            quizTitle = view.findViewById(R.id.leaderName);
            quizPoints = view.findViewById(R.id.leaderPoints);
        }
    }
}
