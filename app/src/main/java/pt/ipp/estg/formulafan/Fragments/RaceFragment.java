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

public class RaceFragment extends Fragment {

    private FragmentManager fragmentManager;
    private CurrentRaceFragment currentRaceFragment;
    private PastRaceFragment pastRaceFragment;
    private TabLayout.Tab currentTab;
    private Context context;

    public RaceFragment() {
        currentRaceFragment = new CurrentRaceFragment();
        pastRaceFragment = new PastRaceFragment();
        currentTab = null;
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
        View view = inflater.inflate(R.layout.fragment_race, container, false);

        if (!currentRaceFragment.isAdded() && !pastRaceFragment.isAdded()) {
            fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.raceFragmentContainer, currentRaceFragment);
            fragmentTransaction.commit();
        }

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        if (currentTab != null) {
            tabLayout.selectTab(currentTab);
        }

        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        currentTab = tab;
                        if (tab.getText() == context.getString(R.string.corridas_anteriores)) {
                            showPastRaceFragment();
                        } else {
                            showCurrentRaceFragment();
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

    private void showCurrentRaceFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.raceFragmentContainer, currentRaceFragment);
        fragmentTransaction.commit();
    }

    private void showPastRaceFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.raceFragmentContainer, pastRaceFragment);
        fragmentTransaction.commit();
    }
}