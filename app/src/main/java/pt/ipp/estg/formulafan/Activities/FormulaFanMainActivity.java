package pt.ipp.estg.formulafan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import pt.ipp.estg.formulafan.Fragments.ProfileFragment;
import pt.ipp.estg.formulafan.Fragments.RaceDetailsFragment;
import pt.ipp.estg.formulafan.Fragments.RaceFragment;
import pt.ipp.estg.formulafan.Fragments.RaceResultDetailsFragment;
import pt.ipp.estg.formulafan.Fragments.ResultsFragment;
import pt.ipp.estg.formulafan.Fragments.StatisticFragment;
import pt.ipp.estg.formulafan.Interfaces.IRaceDetailsListener;
import pt.ipp.estg.formulafan.Interfaces.IRaceResultDetailListener;
import pt.ipp.estg.formulafan.Interfaces.IStatisticsListener;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Models.RaceResult;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Utils.TabletDetectionUtil;

public class FormulaFanMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, IRaceDetailsListener, IStatisticsListener, IRaceResultDetailListener {

    public static final String SELECTED_RACE = "pt.ipp.pt.estg.cmu.selectedRace";

    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private RaceFragment raceFragment;
    private RaceDetailsFragment detailsFragment;
    private BottomNavigationView bottomNavigationView;
    private RaceResultDetailsFragment raceResultDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_fan_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.races);

        raceFragment = new RaceFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerMainUI, raceFragment);
        fragmentTransaction.commit();

        detailsFragment = new RaceDetailsFragment();
        if (TabletDetectionUtil.isTablet(this)) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerMainUIDetails, detailsFragment);
            fragmentTransaction.commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile) {
            changeToProfileFragment();
            return true;
        } else if (item.getItemId() == R.id.races) {
            changeToRaceFragment();
            return true;
        } else {
            changeToResultFragment();
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutButton:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeToProfileFragment() {

        ProfileFragment profileFragment = new ProfileFragment();
        StatisticFragment statisticFragment = new StatisticFragment();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentContainerMainUI, profileFragment);
        fragmentTransaction.addToBackStack(null);

        if (TabletDetectionUtil.isTablet(this)) {
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, statisticFragment);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();

    }

    private void changeToRaceFragment() {
        raceFragment = new RaceFragment();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMainUI, raceFragment);
        fragmentTransaction.addToBackStack(null);

        if (TabletDetectionUtil.isTablet(this)) {
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, detailsFragment);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    private void changeToResultFragment() {
        ResultsFragment resultFragment = new ResultsFragment();
        raceResultDetailsFragment = new RaceResultDetailsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMainUI, resultFragment);
        fragmentTransaction.addToBackStack(null);

        if (TabletDetectionUtil.isTablet(this)) {
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, raceResultDetailsFragment);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    private void logOut() {
        firebaseAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showRaceDetailsView(Race race) {
        if (TabletDetectionUtil.isTablet(this)) {
            detailsFragment.updateRace(race);
        } else {
            Bundle args = new Bundle();
            args.putSerializable(SELECTED_RACE, race);
            detailsFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUI, detailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void changeToStatistics() {
        StatisticFragment statFragment = new StatisticFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMainUI, statFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void showRaceResultDetailsView(RaceResult raceResult) {
        if (TabletDetectionUtil.isTablet(this)) {
            raceResultDetailsFragment.showResults(raceResult);
        } else {
            Bundle args = new Bundle();
            args.putSerializable(SELECTED_RACE, raceResult);
            raceResultDetailsFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUI, raceResultDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}