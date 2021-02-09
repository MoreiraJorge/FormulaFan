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
import pt.ipp.estg.formulafan.Utils.AlarmManagerUtil;
import pt.ipp.estg.formulafan.ViewModels.CurrentRaceViewModel;

public class CurrentRaceFragment extends Fragment {

    private Context context;
    private CurrentRaceRecyclerViewAdapter currentRaceRecyclerViewAdapter;

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
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listCurrentRaces);
        CurrentRaceViewModel currentRaceViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) context.getApplicationContext())).get(CurrentRaceViewModel.class);
        currentRaceRecyclerViewAdapter = new CurrentRaceRecyclerViewAdapter(context);
        recyclerView.setAdapter(currentRaceRecyclerViewAdapter);

        currentRaceViewModel.getAllRaces().observe(getViewLifecycleOwner(), (races) -> {
                    currentRaceRecyclerViewAdapter.setRaceList(races);
                    currentRaceRecyclerViewAdapter.notifyDataSetChanged();
                    if (races.size() != 0) {
                        AlarmManagerUtil.startAlarm(context, races.get(0));
                    }
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}