package pt.ipp.estg.formulafan.WebServices;

import java.util.List;

import pt.ipp.estg.formulafan.Models.DriverPosition;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IDriverPositionDetailsAPI {

    public static String url = "https://ergast.com/api/f1/";

    @GET("current/driverStandings.json")
    Call<List<DriverPosition>> getListOfDriversPositions();
}
