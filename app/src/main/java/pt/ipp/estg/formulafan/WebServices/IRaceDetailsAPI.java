package pt.ipp.estg.formulafan.WebServices;

import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IRaceDetailsAPI {

    public static String url = "https://ergast.com/api/f1/";

    @GET("2021.json")
    Call<List<Race>> getListOfCurrentRaces();

    @GET("current.json")
    Call<List<Race>> getListOfPastRaces();
}
