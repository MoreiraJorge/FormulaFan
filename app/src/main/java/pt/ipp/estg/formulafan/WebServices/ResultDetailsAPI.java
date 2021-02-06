package pt.ipp.estg.formulafan.WebServices;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import pt.ipp.estg.formulafan.Models.RaceResult;
import pt.ipp.estg.formulafan.Models.RaceResultListDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultDetailsAPI {

    public static IResultDetailsAPI resultDetailsAPI = new Retrofit.Builder().baseUrl(IResultDetailsAPI.url)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().registerTypeAdapter(new TypeToken<List<RaceResult>>() {
            }.getType(), new RaceResultListDeserializer()).create())).build().create(IResultDetailsAPI.class);
}
