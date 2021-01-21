package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Databases.RaceDAO;
import pt.ipp.estg.formulafan.Databases.RaceDatabase;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Services.FormulaOneAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaceRepository {

    private RaceDAO raceDAO;
    private LiveData<List<Race>> allRaces;
    private Context toastContext;

    public RaceRepository(Application application) {
        toastContext = application.getApplicationContext();
        RaceDatabase db = RaceDatabase.getDatabase(application);
        raceDAO = db.getRaceDAO();
        allRaces = raceDAO.getRaces();
        refreshCurrentRaces();
    }

    public LiveData<List<Race>> getAllRaces() {
        return  allRaces;
    }

    public void insertRace(Race race) {
        RaceDatabase.databaseWriteExecutor.execute(() -> {raceDAO.insertRace(race);});
    }

    public void deleteRace(Race race){
        RaceDatabase.databaseWriteExecutor.execute(() -> {raceDAO.deleteRace(race);});
    }

    public void refreshCurrentRaces() {
        FormulaOneAPI.formulaOneAPI.getListOfCurrentRaces().enqueue(new Callback<List<Race>>() {
            @Override
            public void onResponse(Call<List<Race>> call, Response<List<Race>> response) {
                List<Race> raceList = response.body();
                RaceDatabase.databaseWriteExecutor.execute(() -> {raceDAO.insertAllRaces(raceList);});
                RaceDatabase.databaseWriteExecutor.execute(() -> {allRaces = raceDAO.getRaces();});
            }

            @Override
            public void onFailure(Call<List<Race>> call, Throwable t) {
                Toast.makeText(toastContext, "Erro ao processar Pedido!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
