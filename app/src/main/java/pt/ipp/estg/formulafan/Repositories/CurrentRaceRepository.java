package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.ipp.estg.formulafan.Databases.CurrentRaceDatabase;
import pt.ipp.estg.formulafan.Databases.RaceDAO;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Services.RaceDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentRaceRepository {

    private final RaceDAO raceDAO;
    private LiveData<List<Race>> allRaces;
    private final Context toastContext;

    public CurrentRaceRepository(Application application) {
        toastContext = application.getApplicationContext();
        CurrentRaceDatabase db = CurrentRaceDatabase.getDatabase(application);
        raceDAO = db.getRaceDAO();
        allRaces = raceDAO.getRaces();
        refreshCurrentRaces();
    }

    public LiveData<List<Race>> getAllRaces() {
        return allRaces;
    }

    public void insertRace(Race race) {
        CurrentRaceDatabase.databaseWriteExecutor.execute(() -> {
            raceDAO.insertRace(race);
        });
    }

    public void deleteRace(Race race) {
        CurrentRaceDatabase.databaseWriteExecutor.execute(() -> {
            raceDAO.deleteRace(race);
        });
    }

    public void refreshCurrentRaces() {
        RaceDetailsAPI.raceDetailsAPI.getListOfCurrentRaces().enqueue(new Callback<List<Race>>() {
            @Override
            public void onResponse(@NotNull Call<List<Race>> call, @NotNull Response<List<Race>> response) {
                List<Race> raceList = response.body();
                CurrentRaceDatabase.databaseWriteExecutor.execute(() -> {
                    raceDAO.insertAllRaces(raceList);
                });
                CurrentRaceDatabase.databaseWriteExecutor.execute(() -> {
                    allRaces = raceDAO.getRaces();
                });
            }

            @Override
            public void onFailure(@NotNull Call<List<Race>> call, @NotNull Throwable t) {
                Toast.makeText(toastContext, "Erro ao processar Pedido!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
