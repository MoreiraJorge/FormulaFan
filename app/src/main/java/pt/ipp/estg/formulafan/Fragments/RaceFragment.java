package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.RaceViewModel;

public class RaceFragment extends Fragment {

    private Context context;
    private RaceViewModel raceViewModel;
    private TabLayout tabLayout;
    private RaceRecyclerViewAdapter raceRecyclerViewAdapter;

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

        Context context = view.getContext();
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        Log.d("Test", "Mudou de tab!");
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        Log.d("Test", "Mudou de tab!");
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        Log.d("Test", "Selecionou a mesma tab!");
                    }
                }
        );

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        raceViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((Application) context.getApplicationContext())).get(RaceViewModel.class);
        raceRecyclerViewAdapter = new RaceRecyclerViewAdapter(context, raceViewModel);
        recyclerView.setAdapter(raceRecyclerViewAdapter);

        raceViewModel.getAllRaces().observe(getViewLifecycleOwner(), (races) -> {
                    raceRecyclerViewAdapter.setRaceList(races);
                    raceRecyclerViewAdapter.notifyDataSetChanged();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}