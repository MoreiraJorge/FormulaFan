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
import pt.ipp.estg.formulafan.ViewModels.RaceResultViewModel;

public class RaceResultFragment extends Fragment {

    private Context context;
    private RaceResultRecyclerViewAdapter raceResultRecyclerViewAdapter;

    public RaceResultFragment() {
    }

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
        View view = inflater.inflate(R.layout.fragment_race_result_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listRaceResults);
        RaceResultViewModel raceResultViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) context.getApplicationContext())).get(RaceResultViewModel.class);
        raceResultRecyclerViewAdapter = new RaceResultRecyclerViewAdapter(context);
        recyclerView.setAdapter(raceResultRecyclerViewAdapter);

        raceResultViewModel.getAllRacesResults().observe(getViewLifecycleOwner(), (raceResults) -> {
                    raceResultRecyclerViewAdapter.setRaceResultList(raceResults);
                    raceResultRecyclerViewAdapter.notifyDataSetChanged();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}