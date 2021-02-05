package pt.ipp.estg.formulafan.Services;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import pt.ipp.estg.formulafan.Models.DriverPosition;
import pt.ipp.estg.formulafan.Models.DriverPositionListDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverPositionDetailsAPI {

    public static IDriverPositionDetailsAPI driverPositionDetailsAPI = new Retrofit.Builder().baseUrl(IDriverPositionDetailsAPI.url)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().registerTypeAdapter(new TypeToken<List<DriverPosition>>() {
            }.getType(), new DriverPositionListDeserializer()).create())).build().create(IDriverPositionDetailsAPI.class);

}
