package pt.ipp.estg.formulafan.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Models.RaceResult;
import pt.ipp.estg.formulafan.Repositories.RaceResultRepository;

public class RaceResultViewModel extends AndroidViewModel {

    private final LiveData<List<RaceResult>> raceResults;
    private final RaceResultRepository raceResultRepository;

    public RaceResultViewModel(@NonNull Application application) {
        super(application);
        raceResultRepository = new RaceResultRepository(application);
        raceResults = raceResultRepository.getAllRacesResults();
    }

    public LiveData<List<RaceResult>> getAllRacesResults() {
        return raceResults;
    }

    public void insertRaceResult(RaceResult raceResult) {
        raceResultRepository.insertRaceResult(raceResult);
    }

    public void deleteRaceResult(RaceResult raceResult) {
        raceResultRepository.deleteRaceResult(raceResult);
    }
}
