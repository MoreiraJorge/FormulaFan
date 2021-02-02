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
import pt.ipp.estg.formulafan.ViewModels.PastRaceViewModel;

public class PastRaceFragment extends Fragment {

    private Context context;
    private PastRaceViewModel pastRaceViewModel;
    private PastRaceRecyclerViewAdapter pastRaceRecyclerViewAdapter;

    public PastRaceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_past_race_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listPastRaces);
        pastRaceViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) context.getApplicationContext())).get(PastRaceViewModel.class);
        pastRaceRecyclerViewAdapter = new PastRaceRecyclerViewAdapter(context);
        recyclerView.setAdapter(pastRaceRecyclerViewAdapter);

        pastRaceViewModel.getAllRaces().observe(getViewLifecycleOwner(), (races) -> {
                    pastRaceRecyclerViewAdapter.setRaceList(races);
                    pastRaceRecyclerViewAdapter.notifyDataSetChanged();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}