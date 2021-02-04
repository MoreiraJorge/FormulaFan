package pt.ipp.estg.formulafan.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.TeamPosition;
import pt.ipp.estg.formulafan.R;

public class TeamPositionDetailsFragment extends Fragment {

    private TeamPosition currentTeamPosition;
    private ConstraintLayout messageView;
    private LinearLayout detailsView;
    private TextView nameView;
    private TextView nationalityView;
    private TextView winsView;
    private Toolbar toolbar;

    public TeamPositionDetailsFragment() {
        this.currentTeamPosition = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle teamBundle = this.getArguments();
        if (teamBundle != null) {
            this.currentTeamPosition = (TeamPosition) teamBundle.getSerializable(FormulaFanMainActivity.SELECTED_TEAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_position_details, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        messageView = view.findViewById(R.id.messageView);
        detailsView = view.findViewById(R.id.teamDetailsView);
        nameView = view.findViewById(R.id.nameView);
        nationalityView = view.findViewById(R.id.nationalityView);
        winsView = view.findViewById(R.id.winsView);

        if (currentTeamPosition != null) {
            messageView.setVisibility(View.GONE);
            detailsView.setVisibility(View.VISIBLE);
            toolbar.setTitle(currentTeamPosition.team.name + " - Detalhes");
            String name = " " + currentTeamPosition.team.name;
            nameView.setText(name);
            String nationality = " " + currentTeamPosition.team.teamNationality;
            nationalityView.setText(nationality);
            String wins = " " + currentTeamPosition.wins;
            winsView.setText(wins);
        }

        return view;
    }
}