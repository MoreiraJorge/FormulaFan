package pt.ipp.estg.formulafan.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Repositories.PastRaceRepository;

public class PastRaceViewModel extends AndroidViewModel {

    private final LiveData<List<Race>> races;
    private final PastRaceRepository pastRaceRepository;

    public PastRaceViewModel(@NonNull Application application) {
        super(application);
        pastRaceRepository = new PastRaceRepository(application);
        races = pastRaceRepository.getAllRaces();
    }

    public LiveData<List<Race>> getAllRaces() {
        return races;
    }

    public void insertRace(Race race) {
        pastRaceRepository.insertRace(race);
    }

    public void deleteRace(Race race) {
        pastRaceRepository.deleteRace(race);
    }
}
