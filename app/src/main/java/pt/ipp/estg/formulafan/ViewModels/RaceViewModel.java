package pt.ipp.estg.formulafan.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Repositories.RaceRepository;
import pt.ipp.estg.formulafan.Models.Race;

public class RaceViewModel extends AndroidViewModel {

    private final LiveData<List<Race>> races;
    private RaceRepository raceRepository;

    public RaceViewModel(@NonNull Application application) {
        super(application);
        raceRepository = new RaceRepository(application);
        races = raceRepository.getAllRaces();
    }

    public LiveData<List<Race>> getAllRaces() { return  races;}

    public void insertRace(Race race) {raceRepository.insertRace(race);}

    public void deleteRace(Race race) {raceRepository.deleteRace(race);}
}
