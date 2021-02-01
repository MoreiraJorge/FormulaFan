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
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.Models.Circuit;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;

public class RaceDetailsFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private FragmentActivity context;
    private ConstraintLayout messageView;
    private LinearLayout detailsView;
    private TextView seasonView;
    private TextView roundView;
    private TextView dateView;
    private TextView circuitView;
    private Toolbar toolbar;
    private Race currentRace;

    public RaceDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (FragmentActivity) context;
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
        seasonView = view.findViewById(R.id.seasonView);
        detailsView = view.findViewById(R.id.detailsView);
        roundView = view.findViewById(R.id.roundView);
        dateView = view.findViewById(R.id.dateView);
        circuitView = view.findViewById(R.id.circuitView);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapLocation);
        mapFragment.getMapAsync(this);

        if (currentRace != null) {
            messageView.setVisibility(View.GONE);
            detailsView.setVisibility(View.VISIBLE);
            toolbar.setTitle(currentRace.raceName + " - Detalhes");
            seasonView.setText(" " + currentRace.season);
            roundView.setText(" " + currentRace.round);
            dateView.setText(" " + currentRace.date.toLocaleString());
            circuitView.setText(" " + currentRace.circuit.circuitName);
        }

        return view;
    }

    public void updateRace(Race race) {
        messageView.setVisibility(View.GONE);
        detailsView.setVisibility(View.VISIBLE);
        toolbar.setTitle(race.raceName + " - Detalhes");
        seasonView.setText(" " + race.season);
        roundView.setText(" " + race.round);
        dateView.setText(" " + race.date.toLocaleString());
        circuitView.setText(" " + race.circuit.circuitName);
        addMarker(race.circuit);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (currentRace != null) {
            addMarker(currentRace.circuit);
        }
    }

    private void addMarker(Circuit circuit) {
        LatLng latLng = new LatLng(circuit.location.lat, circuit.location.lng);
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(circuit.circuitName)
                .snippet(circuit.location.locality)
        );
        zoomToLocation(latLng);
    }

    private void zoomToLocation(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        googleMap.animateCamera(cameraUpdate);
    }
}