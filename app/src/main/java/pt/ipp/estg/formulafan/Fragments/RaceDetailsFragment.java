package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;

public class RaceDetailsFragment extends Fragment {

    private ConstraintLayout messageView;
    private LinearLayout detailsView;
    private TextView raceName;
    private Toolbar toolbar;
    private Race currentRace;

    public RaceDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle raceBundle = this.getArguments();
        if (raceBundle != null) {
            currentRace = (Race) raceBundle.getSerializable(FormulaFanMainActivity.SELECTED_RACE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_details, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.detalhes_corrida);
        messageView = view.findViewById(R.id.messageView);
        raceName = view.findViewById(R.id.raceNameView);
        detailsView = view.findViewById(R.id.detailsView);
        if (currentRace != null) {
            messageView.setVisibility(View.GONE);
            detailsView.setVisibility(View.VISIBLE);
            toolbar.setTitle(currentRace.raceName + " - Detalhes");
            raceName.setText(currentRace.raceName);
        }
        return view;
    }

    public void updateRace(Race race) {
        messageView.setVisibility(View.GONE);
        detailsView.setVisibility(View.VISIBLE);
        toolbar.setTitle(race.raceName + " - Detalhes");
        raceName.setText(race.raceName);
    }
}