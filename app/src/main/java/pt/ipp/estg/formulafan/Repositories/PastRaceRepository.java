package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.ipp.estg.formulafan.Databases.PastRaceDatabase;
import pt.ipp.estg.formulafan.Databases.RaceDAO;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.WebServices.RaceDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastRaceRepository {

    private RaceDAO raceDAO;
    private LiveData<List<Race>> allRaces;
    private Context toastContext;

    public PastRaceRepository(Application application) {
        toastContext = application.getApplicationContext();
        PastRaceDatabase db = PastRaceDatabase.getDatabase(application);
        raceDAO = db.getRaceDAO();
        allRaces = raceDAO.getRaces();
        refreshPastRaces();
    }

    public LiveData<List<Race>> getAllRaces() {
        return allRaces;
    }

    public void insertRace(Race race) {
        PastRaceDatabase.databaseWriteExecutor.execute(() -> {
            raceDAO.insertRace(race);
        });
    }

    public void deleteRace(Race race) {
        PastRaceDatabase.databaseWriteExecutor.execute(() -> {
            raceDAO.deleteRace(race);
        });
    }

    public void refreshPastRaces() {
        RaceDetailsAPI.raceDetailsAPI.getListOfPastRaces().enqueue(new Callback<List<Race>>() {
            @Override
            public void onResponse(@NotNull Call<List<Race>> call, @NotNull Response<List<Race>> response) {
                List<Race> raceList = response.body();
                PastRaceDatabase.databaseWriteExecutor.execute(() -> {
                    raceDAO.insertAllRaces(raceList);
                });
                PastRaceDatabase.databaseWriteExecutor.execute(() -> {
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
