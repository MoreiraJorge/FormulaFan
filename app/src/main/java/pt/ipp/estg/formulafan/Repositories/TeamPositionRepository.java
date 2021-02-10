package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.ipp.estg.formulafan.Databases.TeamPositionDAO;
import pt.ipp.estg.formulafan.Databases.TeamPositionDatabase;
import pt.ipp.estg.formulafan.Models.TeamPosition;
import pt.ipp.estg.formulafan.WebServices.TeamPositionDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamPositionRepository {

    private final TeamPositionDAO teamPositionDAO;
    private final Context toastContext;
    private LiveData<List<TeamPosition>> allTeamsPositions;

    public TeamPositionRepository(Application application) {
        toastContext = application.getApplicationContext();
        TeamPositionDatabase db = TeamPositionDatabase.getDatabase(application);
        teamPositionDAO = db.getTeamPositionDAO();
        allTeamsPositions = teamPositionDAO.getTeamsPositions();
        refreshTeamsPositions();
    }

    public LiveData<List<TeamPosition>> getAllTeamsPositions() {
        return allTeamsPositions;
    }

    public void insertTeamPosition(TeamPosition teamPosition) {
        TeamPositionDatabase.databaseWriteExecutor.execute(() -> {
            teamPositionDAO.insertTeamsPosition(teamPosition);
        });
    }

    public void deleteTeamPosition(TeamPosition teamPosition) {
        TeamPositionDatabase.databaseWriteExecutor.execute(() -> {
            teamPositionDAO.deleteTeamsPosition(teamPosition);
        });
    }

    public void refreshTeamsPositions() {
        TeamPositionDetailsAPI.teamPositionDetailsAPI.getListOfTeamsPositions().enqueue(new Callback<List<TeamPosition>>() {
            @Override
            public void onResponse(@NotNull Call<List<TeamPosition>> call, @NotNull Response<List<TeamPosition>> response) {
                List<TeamPosition> teamPositionList = response.body();
                TeamPositionDatabase.databaseWriteExecutor.execute(() -> {
                    teamPositionDAO.insertAllTeamsPositions(teamPositionList);
                });
                TeamPositionDatabase.databaseWriteExecutor.execute(() -> {
                    allTeamsPositions = teamPositionDAO.getTeamsPositions();
                });
            }

            @Override
            public void onFailure(@NotNull Call<List<TeamPosition>> call, @NotNull Throwable t) {
                Toast.makeText(toastContext, "Erro ao processar Pedido!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
