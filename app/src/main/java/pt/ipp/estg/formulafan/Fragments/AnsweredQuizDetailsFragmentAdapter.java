package pt.ipp.estg.formulafan.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.ipp.estg.formulafan.Models.QuestionAnswered;
import pt.ipp.estg.formulafan.R;

public class AnsweredQuizDetailsFragmentAdapter extends RecyclerView.Adapter<AnsweredQuizDetailsFragmentAdapter.ViewHolder> {

    List<QuestionAnswered> answeredQuestions;

    public AnsweredQuizDetailsFragmentAdapter(List<QuestionAnswered> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View quizzHistoryView = inflater
                .inflate(R.layout.fragment_answered_quiz_details_item, parent, false);

        return new AnsweredQuizDetailsFragmentAdapter.ViewHolder(quizzHistoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionAnswered questionAnswered = answeredQuestions.get(position);
        holder.questionTitle.setText(questionAnswered.title);
        holder.questionAnswer.setText(questionAnswered.answer);
        holder.correctOrWrong.setText(questionAnswered.correct);
    }

    public void setAnsweredQuestions(List<QuestionAnswered> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    @Override
    public int getItemCount() {
        return this.answeredQuestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView questionTitle;
        public final TextView questionAnswer;
        public final TextView correctOrWrong;

        public ViewHolder(View view) {
            super(view);
            questionTitle = view.findViewById(R.id.leaderName);
            questionAnswer = view.findViewById(R.id.leaderPointsiew);
            correctOrWrong = view.findViewById(R.id.leaderPoints);
        }
    }
}
