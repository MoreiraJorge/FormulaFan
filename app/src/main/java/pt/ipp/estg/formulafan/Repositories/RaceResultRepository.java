package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.ipp.estg.formulafan.Databases.RaceResultDAO;
import pt.ipp.estg.formulafan.Databases.RaceResultDatabase;
import pt.ipp.estg.formulafan.Models.RaceResult;
import pt.ipp.estg.formulafan.WebServices.ResultDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaceResultRepository {

    private final RaceResultDAO raceResultDAO;
    private LiveData<List<RaceResult>> allRacesResults;
    private final Context toastContext;

    public RaceResultRepository(Application application) {
        toastContext = application.getApplicationContext();
        RaceResultDatabase db = RaceResultDatabase.getDatabase(application);
        raceResultDAO = db.getRaceResultDAO();
        allRacesResults = raceResultDAO.getRacesResults();
        refreshRacesResults();
    }

    public LiveData<List<RaceResult>> getAllRacesResults() {
        return allRacesResults;
    }

    public void insertRaceResult(RaceResult raceResult) {
        RaceResultDatabase.databaseWriteExecutor.execute(() -> {
            raceResultDAO.insertRaceResult(raceResult);
        });
    }

    public void deleteRaceResult(RaceResult raceResult) {
        RaceResultDatabase.databaseWriteExecutor.execute(() -> {
            raceResultDAO.deleteRaceResult(raceResult);
        });
    }

    public void refreshRacesResults() {
        ResultDetailsAPI.resultDetailsAPI.getListOfRaceResults().enqueue(new Callback<List<RaceResult>>() {
            @Override
            public void onResponse(@NotNull Call<List<RaceResult>> call, @NotNull Response<List<RaceResult>> response) {
                List<RaceResult> raceResultList = response.body();
                RaceResultDatabase.databaseWriteExecutor.execute(() -> {
                    raceResultDAO.insertAllRacesResults(raceResultList);
                });
                RaceResultDatabase.databaseWriteExecutor.execute(() -> {
                    allRacesResults = raceResultDAO.getRacesResults();
                });
            }

            @Override
            public void onFailure(@NotNull Call<List<RaceResult>> call, @NotNull Throwable t) {
                Toast.makeText(toastContext, "Erro ao processar Pedido!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
