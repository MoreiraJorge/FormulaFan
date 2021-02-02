package pt.ipp.estg.formulafan.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Repositories.CurrentRaceRepository;

public class CurrentRaceViewModel extends AndroidViewModel {

    private final LiveData<List<Race>> races;
    private final CurrentRaceRepository currentRaceRepository;

    public CurrentRaceViewModel(@NonNull Application application) {
        super(application);
        currentRaceRepository = new CurrentRaceRepository(application);
        races = currentRaceRepository.getAllRaces();
    }

    public LiveData<List<Race>> getAllRaces() {
        return races;
    }

    public void insertRace(Race race) {
        currentRaceRepository.insertRace(race);
    }

    public void deleteRace(Race race) {
        currentRaceRepository.deleteRace(race);
    }
}
