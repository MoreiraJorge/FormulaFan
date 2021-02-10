package pt.ipp.estg.formulafan.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Models.TeamPosition;
import pt.ipp.estg.formulafan.Repositories.TeamPositionRepository;

public class TeamPositionViewModel extends AndroidViewModel {

    private final LiveData<List<TeamPosition>> teamsPositions;
    private final TeamPositionRepository teamPositionRepository;

    public TeamPositionViewModel(@NonNull Application application) {
        super(application);
        teamPositionRepository = new TeamPositionRepository(application);
        teamsPositions = teamPositionRepository.getAllTeamsPositions();
    }

    public LiveData<List<TeamPosition>> getAllTeamsPositions() {
        return teamsPositions;
    }

    public void insertTeamPosition(TeamPosition teamPosition) {
        teamPositionRepository.insertTeamPosition(teamPosition);
    }

    public void deleteTeamPosition(TeamPosition teamPosition) {
        teamPositionRepository.deleteTeamPosition(teamPosition);
    }

}
