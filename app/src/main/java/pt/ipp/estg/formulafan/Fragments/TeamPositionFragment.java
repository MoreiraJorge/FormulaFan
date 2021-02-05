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

import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.TeamPositionViewModel;

public class TeamPositionFragment extends Fragment {

    private Context context;
    private TeamPositionRecyclerViewAdapter teamPositionRecyclerViewAdapter;

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
        View view = inflater.inflate(R.layout.fragment_team_position_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.teamPositionList);
        TeamPositionViewModel teamPositionViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) context.getApplicationContext())).get(TeamPositionViewModel.class);
        teamPositionRecyclerViewAdapter = new TeamPositionRecyclerViewAdapter(context);
        recyclerView.setAdapter(teamPositionRecyclerViewAdapter);

        teamPositionViewModel.getAllTeamsPositions().observe(getViewLifecycleOwner(), (teamPositions) -> {
                    teamPositionRecyclerViewAdapter.setTeamPositionList(teamPositions);
                    teamPositionRecyclerViewAdapter.notifyDataSetChanged();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}