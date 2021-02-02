package pt.ipp.estg.formulafan.Services;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.Models.RaceListDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RaceDetailsAPI {

    public static IRaceDetailsAPI raceDetailsAPI = new Retrofit.Builder().baseUrl(IRaceDetailsAPI.url)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().registerTypeAdapter(new TypeToken<List<Race>>() {
            }.getType(), new RaceListDeserializer()).create())).build().create(IRaceDetailsAPI.class);

}
