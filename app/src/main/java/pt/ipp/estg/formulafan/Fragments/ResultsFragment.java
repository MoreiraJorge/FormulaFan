package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import pt.ipp.estg.formulafan.R;

public class ResultsFragment extends Fragment {

    private FragmentManager fragmentManager;
    private RaceResultFragment raceResultFragment;
    private DriverPositionFragment driverPositionFragment;
    private TabLayout.Tab currentTab;
    private Context context;

    public ResultsFragment() {
        this.raceResultFragment = new RaceResultFragment();
        this.driverPositionFragment = new DriverPositionFragment();
        this.currentTab = null;
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
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        if (!raceResultFragment.isAdded() && !driverPositionFragment.isAdded()) { //Falta o outro fragment
            fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.resultsFragmentContainer, raceResultFragment);
            fragmentTransaction.commit();
        }

        TabLayout tabLayout = view.findViewById(R.id.resultsTabLayout);

        if (currentTab != null) {
            tabLayout.selectTab(currentTab);
        }

        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        currentTab = tab;
                        if (tab.getText() == context.getString(R.string.pilotos)) {
                            showDriverPositionFragment();
                        } else if (tab.getText() == context.getString(R.string.resultados)) {
                            showRaceResultsFragment();
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                }
        );

        return view;
    }

    private void showRaceResultsFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.resultsFragmentContainer, raceResultFragment);
        fragmentTransaction.commit();
    }

    private void showDriverPositionFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.resultsFragmentContainer, driverPositionFragment);
        fragmentTransaction.commit();
    }
}