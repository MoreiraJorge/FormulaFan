package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.RaceResult;
import pt.ipp.estg.formulafan.R;

public class RaceResultDetailsFragment extends Fragment {

    private Context context;
    private RaceResult raceResult;
    private ConstraintLayout messageView;
    private Toolbar toolbar;
    private LinearLayout resultDetailsView;
    private RecyclerView recyclerView;
    private RaceResultDetailsRecyclerViewAdapter resultRecyclerViewAdapter;

    public RaceResultDetailsFragment() {
        this.raceResult = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle raceResultBundle = this.getArguments();
        if (raceResultBundle != null) {
            raceResult = (RaceResult) raceResultBundle.getSerializable(FormulaFanMainActivity.SELECTED_RACE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_result_details_list, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        messageView = view.findViewById(R.id.messageView);
        resultDetailsView = view.findViewById(R.id.resultDetailsView);
        recyclerView = (RecyclerView) view.findViewById(R.id.listRaceResultDetails);

        if (raceResult != null) {
            messageView.setVisibility(View.GONE);
            resultDetailsView.setVisibility(View.VISIBLE);
            toolbar.setTitle(raceResult.raceName + " - Resultados da Corrida");
            resultRecyclerViewAdapter = new RaceResultDetailsRecyclerViewAdapter(this.raceResult.results);
            recyclerView.setAdapter(resultRecyclerViewAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
        }

        return view;
    }

    public void showResults(RaceResult raceResult) {
        this.raceResult = raceResult;
        messageView.setVisibility(View.GONE);
        resultDetailsView.setVisibility(View.VISIBLE);
        toolbar.setTitle(raceResult.raceName + " - Resultados da Corrida");
        resultRecyclerViewAdapter = new RaceResultDetailsRecyclerViewAdapter(this.raceResult.results);
        recyclerView.setAdapter(resultRecyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }
}