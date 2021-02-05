package pt.ipp.estg.formulafan.WebServices;

import java.util.List;

import pt.ipp.estg.formulafan.Models.TeamPosition;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ITeamPositionDetailsAPI {

    public static String url = "https://ergast.com/api/f1/";

    @GET("current/constructorStandings.json")
    Call<List<TeamPosition>> getListOfTeamsPositions();
}
