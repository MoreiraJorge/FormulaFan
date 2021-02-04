package pt.ipp.estg.formulafan.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.ipp.estg.formulafan.Models.Result;
import pt.ipp.estg.formulafan.R;

public class RaceResultDetailsRecyclerViewAdapter extends RecyclerView.Adapter<RaceResultDetailsRecyclerViewAdapter.ViewHolder> {

    private List<Result> results;

    public RaceResultDetailsRecyclerViewAdapter(List<Result> results) {
        this.results = results;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_race_result_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.result = results.get(position);
        String result = "" + holder.result.position + " - " + holder.result.driverName + " - " + holder.result.teamName;
        holder.textView.setText(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResultList(List<Result> resultList) {
        this.results = resultList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textView;
        public Result result;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = view.findViewById(R.id.raceResultPosition);
        }
    }
}