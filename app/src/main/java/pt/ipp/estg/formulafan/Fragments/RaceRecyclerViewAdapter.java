package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.RaceViewModel;

public class RaceRecyclerViewAdapter extends RecyclerView.Adapter<RaceRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Race> raceList;
    private RaceViewModel raceViewModel;

    public RaceRecyclerViewAdapter(Context context, RaceViewModel raceViewModel) {
        this.raceList = new ArrayList<>();
        this.context = context;
        this.raceViewModel = raceViewModel;
    }

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
    }

    @Override
    public int getItemCount() {
        return raceList.size();
    }

    public void setRaceList(List<Race> raceList) {
        this.raceList = raceList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textView;
        public Race race;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = (TextView) view.findViewById(R.id.content);
        }
    }
}