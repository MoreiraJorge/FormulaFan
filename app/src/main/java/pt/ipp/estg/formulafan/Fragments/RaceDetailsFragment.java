package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;

public class RaceDetailsFragment extends Fragment {

    private TextView message;
    private Race currentRace;

    public RaceDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle raceBundle = this.getArguments();
        if(raceBundle != null) {
            currentRace = (Race) raceBundle.getSerializable(FormulaFanMainActivity.SELECTED_RACE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_details, container, false);
        message = view.findViewById(R.id.messageView);
        if(currentRace != null) {
            message.setText(currentRace.raceName);
        }
        return view;
    }

    public void updateRace(Race race) {
        message.setText(race.raceName);
    }
}