package pt.ipp.estg.formulafan.Services;

import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FormulaOneAPI {

    public static String url = "https://ergast.com/api/f1/";

    @GET("current.json")
    Call<List<Race>> getListOfCurrentRaces();

}
