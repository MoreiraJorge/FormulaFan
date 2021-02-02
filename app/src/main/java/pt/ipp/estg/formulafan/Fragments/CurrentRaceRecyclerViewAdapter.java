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

import pt.ipp.estg.formulafan.Interfaces.IRaceDetailsListener;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;

public class CurrentRaceRecyclerViewAdapter extends RecyclerView.Adapter<CurrentRaceRecyclerViewAdapter.ViewHolder> {

    private IRaceDetailsListener raceDetailsListener;
    private List<Race> raceList;

    public CurrentRaceRecyclerViewAdapter(Context context) {
        this.raceList = new ArrayList<>();
        this.raceDetailsListener = (IRaceDetailsListener) context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.race = raceList.get(position);
        holder.textView.setText(raceList.get(position).raceName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raceDetailsListener.showRaceDetailsView(raceList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return raceList.size();
    }

    public void setRaceList(List<Race> raceList) {
        this.raceList = raceList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textView;
        public Race race;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = view.findViewById(R.id.content);
        }
    }
}