package pt.ipp.estg.formulafan.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

import pt.ipp.estg.formulafan.Fragments.AnsweredQuizDetailsFragment;
import pt.ipp.estg.formulafan.Fragments.DriverPositionDetailsFragment;
import pt.ipp.estg.formulafan.Fragments.ProfileFragment;
import pt.ipp.estg.formulafan.Fragments.QiLeaderFragment;
import pt.ipp.estg.formulafan.Fragments.QuizzHistoryFragment;
import pt.ipp.estg.formulafan.Fragments.RaceDetailsFragment;
import pt.ipp.estg.formulafan.Fragments.RaceFragment;
import pt.ipp.estg.formulafan.Fragments.RaceResultDetailsFragment;
import pt.ipp.estg.formulafan.Fragments.ResultsFragment;
import pt.ipp.estg.formulafan.Fragments.StatisticFragment;
import pt.ipp.estg.formulafan.Fragments.TeamPositionDetailsFragment;
import pt.ipp.estg.formulafan.Interfaces.IDriverDetailsListener;
import pt.ipp.estg.formulafan.Interfaces.IQuizHistoryListener;
import pt.ipp.estg.formulafan.Interfaces.IQuizLeaderListener;
import pt.ipp.estg.formulafan.Interfaces.IRaceDetailsListener;
import pt.ipp.estg.formulafan.Interfaces.IRaceResultDetailsListener;
import pt.ipp.estg.formulafan.Interfaces.IStatisticsListener;
import pt.ipp.estg.formulafan.Interfaces.ITeamDetailsListener;
import pt.ipp.estg.formulafan.Models.DriverPosition;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Models.RaceResult;
import pt.ipp.estg.formulafan.Models.TeamPosition;
import pt.ipp.estg.formulafan.NativeServices.AlarmBroadcastReceiver;
import pt.ipp.estg.formulafan.NativeServices.GeofenceBroadcastReceiver;
import pt.ipp.estg.formulafan.NativeServices.QuizService;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Utils.AlarmManagerUtil;
import pt.ipp.estg.formulafan.Utils.InternetUtil;
import pt.ipp.estg.formulafan.Utils.ServiceUtil;
import pt.ipp.estg.formulafan.Utils.TabletDetectionUtil;
import pt.ipp.estg.formulafan.ViewModels.CurrentRaceViewModel;
import pt.ipp.estg.formulafan.ViewModels.PastRaceViewModel;

import static pt.ipp.estg.formulafan.NativeServices.GeofenceBroadcastReceiver.CIRCUIT;

public class FormulaFanMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        IRaceDetailsListener, IRaceResultDetailsListener,
        IDriverDetailsListener, ITeamDetailsListener, IStatisticsListener,
        IQuizHistoryListener,
        IQuizLeaderListener {

    public static final String SELECTED_RACE = "pt.ipp.pt.estg.cmu.selectedRace";
    public static final String SELECTED_QUIZ_DONE = "pt.ipp.pt.estg.cmu.selectedQuizDone";
    public static final String SELECTED_DRIVER = "pt.ipp.pt.estg.cmu.selectedDriver";
    public static final String SELECTED_TEAM = "pt.ipp.pt.estg.cmu.selectedTeam";
    public static final String RUNNING_SERVICE = "pt.ipp.pt.estg.cmu.runningService";
    private static final int REQUEST_LOCATION = 100;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private RaceFragment raceFragment;
    private RaceDetailsFragment detailsFragment;
    private BottomNavigationView bottomNavigationView;
    private AnsweredQuizDetailsFragment answeredQuizDetailsFragment;
    private RaceResultDetailsFragment raceResultDetailsFragment;
    private StatisticFragment statFragment;
    private InternetUtil internetUtil;
    private SharedPreferences sharedPreferences;
    private String circuitName;
    private PastRaceViewModel pastRaceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_fan_main);

        pastRaceViewModel =
                new ViewModelProvider(this,
                        new ViewModelProvider.AndroidViewModelFactory((Application) getApplicationContext())).get(PastRaceViewModel.class);
        shuffleCircuits();

        answeredQuizDetailsFragment = new AnsweredQuizDetailsFragment();

        statFragment = new StatisticFragment();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.races);

        raceFragment = new RaceFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMainUI, raceFragment);
        fragmentTransaction.commit();

        detailsFragment = new RaceDetailsFragment();
        if (TabletDetectionUtil.isTablet(this)) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, detailsFragment);
            fragmentTransaction.commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        internetUtil = new InternetUtil(getApplication());
        internetUtil.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (!isConnected) {
                    Toast.makeText(getApplicationContext(), "Dispositivo Offline!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (!sharedPreferences.getBoolean(RUNNING_SERVICE, true)) {
            if (!ServiceUtil.isMyServiceRunning(QuizService.class, this)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions();
                } else {
                    Intent startService = new Intent(this, QuizService.class);
                    startService(startService);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(RUNNING_SERVICE, true);
                    editor.commit();
                }
            }
        }

        CurrentRaceViewModel currentRaceViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(CurrentRaceViewModel.class);
        currentRaceViewModel.getAllRaces().observe(this, (races) -> {
                    if (races.size() != 0) {
                        AlarmManagerUtil.startAlarm(getApplicationContext(), races.get(0));
                    }
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent startService = new Intent(this, QuizService.class);
                startService(startService);
                editor.putBoolean(RUNNING_SERVICE, true);
                editor.commit();
            } else {
                Toast.makeText(this, "Habilite a premissão de localização para receber desafios!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutButton) {
            logOut();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(QuizService.NOTIFICATION_ID);
            notificationManager.cancel(GeofenceBroadcastReceiver.NOTIFICATION_ID);
            notificationManager.cancel(AlarmBroadcastReceiver.NOTIFICATION_ID);
        } else if (item.getItemId() == R.id.quizButton) {
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra(CIRCUIT, circuitName);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void shuffleCircuits() {
        pastRaceViewModel.getAllRaces().observe(this, (races) -> {
            if (races.size() != 0) {
                ArrayList<Race> racesList = (ArrayList<Race>) races;
                Collections.shuffle(racesList);
                circuitName = racesList.get(0).circuit.circuitName;
            }
        });
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
        if (ServiceUtil.isMyServiceRunning(QuizService.class, this)) {
            stopService(new Intent(this, QuizService.class));
        }
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
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMainUI, statFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void changeToQuizHistory() {

        QuizzHistoryFragment quizzHistoryFragment = new QuizzHistoryFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMainUI, quizzHistoryFragment);
        fragmentTransaction.addToBackStack(null);

        if (TabletDetectionUtil.isTablet(this)) {
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, answeredQuizDetailsFragment);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void showDoneQuizDetails(QuizDone quiz) {
        if (TabletDetectionUtil.isTablet(this)) {
            answeredQuizDetailsFragment.updateQuiz(quiz);
        } else {
            Bundle args = new Bundle();
            args.putSerializable(SELECTED_QUIZ_DONE, quiz);
            answeredQuizDetailsFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUI, answeredQuizDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void changeToQuizLeaderBoard() {
        QiLeaderFragment qiLeaderFragment = new QiLeaderFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (TabletDetectionUtil.isTablet(this)) {
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, qiLeaderFragment);
        } else {
            fragmentTransaction.replace(R.id.fragmentContainerMainUI, qiLeaderFragment);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void showRaceResultDetailsView(RaceResult raceResult) {

        raceResultDetailsFragment = new RaceResultDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(SELECTED_RACE, raceResult);
        raceResultDetailsFragment.setArguments(args);

        if (TabletDetectionUtil.isTablet(this)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, raceResultDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUI, raceResultDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void showDriverDetailsView(DriverPosition driverPosition) {
        DriverPositionDetailsFragment driverPositionDetailsFragment = new DriverPositionDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(SELECTED_DRIVER, driverPosition);
        driverPositionDetailsFragment.setArguments(args);

        if (TabletDetectionUtil.isTablet(this)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, driverPositionDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUI, driverPositionDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void showTeamDetailsView(TeamPosition teamPosition) {
        TeamPositionDetailsFragment teamPositionDetailsFragment = new TeamPositionDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(SELECTED_TEAM, teamPosition);
        teamPositionDetailsFragment.setArguments(args);

        if (TabletDetectionUtil.isTablet(this)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUIDetails, teamPositionDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMainUI, teamPositionDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}