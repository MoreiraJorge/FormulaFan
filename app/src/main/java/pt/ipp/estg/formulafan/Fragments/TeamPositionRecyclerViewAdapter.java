package pt.ipp.estg.formulafan.Fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ipp.estg.formulafan.Interfaces.ITeamDetailsListener;
import pt.ipp.estg.formulafan.Models.TeamPosition;
import pt.ipp.estg.formulafan.R;

import java.util.ArrayList;
import java.util.List;

public class TeamPositionRecyclerViewAdapter extends RecyclerView.Adapter<TeamPositionRecyclerViewAdapter.ViewHolder> {

    private List<TeamPosition> teamPositionList;
    private ITeamDetailsListener teamDetailsListener;

    public TeamPositionRecyclerViewAdapter(Context context) {
        this.teamPositionList = new ArrayList<>();
        this.teamDetailsListener = (ITeamDetailsListener) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team_position, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.teamPosition = teamPositionList.get(position);
        String team = "" + holder.teamPosition.position + " - " + holder.teamPosition.team.name
                + " - " + holder.teamPosition.points + " Pts" ;
        holder.textView.setText(team);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamDetailsListener.showTeamDetailsView(holder.teamPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamPositionList.size();
    }

    public void setTeamPositionList(List<TeamPosition> teamPositionList) {
        this.teamPositionList = teamPositionList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textView;
        public TeamPosition teamPosition;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = view.findViewById(R.id.teamPosition);
        }
    }
}