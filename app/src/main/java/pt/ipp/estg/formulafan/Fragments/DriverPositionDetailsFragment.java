package pt.ipp.estg.formulafan.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.DriverPosition;
import pt.ipp.estg.formulafan.R;

public class DriverPositionDetailsFragment extends Fragment {

    private DriverPosition currentDriverPosition;
    private ConstraintLayout messageView;
    private LinearLayout detailsView;
    private TextView nameView;
    private TextView nationalityView;
    private TextView winsView;
    private TextView numberView;
    private TextView teamView;
    private Toolbar toolbar;

    public DriverPositionDetailsFragment() {
        this.currentDriverPosition = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle driverBundle = this.getArguments();
        if (driverBundle != null) {
            currentDriverPosition = (DriverPosition) driverBundle.getSerializable(FormulaFanMainActivity.SELECTED_DRIVER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_position_details, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        messageView = view.findViewById(R.id.messageView);
        detailsView = view.findViewById(R.id.driverDetailsView);
        nameView = view.findViewById(R.id.nameView);
        nationalityView = view.findViewById(R.id.nationalityView);
        winsView = view.findViewById(R.id.winsView);
        numberView = view.findViewById(R.id.numberView);
        teamView = view.findViewById(R.id.teamView);


        if (currentDriverPosition != null) {
            messageView.setVisibility(View.GONE);
            detailsView.setVisibility(View.VISIBLE);
            toolbar.setTitle(currentDriverPosition.driver.givenName + " " + currentDriverPosition.driver.familyName + " - Detalhes");
            String name = " " + currentDriverPosition.driver.givenName + " " + currentDriverPosition.driver.familyName;
            nameView.setText(name);
            String nationality = " " + currentDriverPosition.driver.nationality;
            nationalityView.setText(nationality);
            String winds = " " + currentDriverPosition.wins;
            winsView.setText(winds);
            String number = " " + currentDriverPosition.driver.permanentNumber;
            numberView.setText(number);
            String team = " " + currentDriverPosition.team.name;
            teamView.setText(team);
        }

        return view;
    }
}