package pt.ipp.estg.formulafan.WebServices;

import java.util.List;

import pt.ipp.estg.formulafan.Models.RaceResult;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IResultDetailsAPI {

    public static String url = "https://ergast.com/api/f1/";

    @GET("current/results.json?limit=1000")
    Call<List<RaceResult>> getListOfRaceResults();

}
