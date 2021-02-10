package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.formulafan.Interfaces.IRaceResultDetailsListener;
import pt.ipp.estg.formulafan.Models.RaceResult;
import pt.ipp.estg.formulafan.R;

public class RaceResultRecyclerViewAdapter extends RecyclerView.Adapter<RaceResultRecyclerViewAdapter.ViewHolder> {

    private final IRaceResultDetailsListener raceResultDetailListener;
    private List<RaceResult> raceResultList;

    public RaceResultRecyclerViewAdapter(Context context) {
        this.raceResultList = new ArrayList<>();
        this.raceResultDetailListener = (IRaceResultDetailsListener) context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_race_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.raceResult = raceResultList.get(position);
        holder.textView.setText(raceResultList.get(position).raceName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raceResultDetailListener.showRaceResultDetailsView(holder.raceResult);
            }
        });
    }

    @Override
    public int getItemCount() {
        return raceResultList.size();
    }

    public void setRaceResultList(List<RaceResult> raceResultList) {
        this.raceResultList = raceResultList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textView;
        public RaceResult raceResult;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = view.findViewById(R.id.raceResultPosition);
        }
    }
}