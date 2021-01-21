package pt.ipp.estg.formulafan.Services;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import pt.ipp.estg.formulafan.Models.RaceListDeserializer;
import pt.ipp.estg.formulafan.Models.Race;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormulaOneAPI {

    public static IFormulaOneAPI formulaOneAPI = new Retrofit.Builder().baseUrl(IFormulaOneAPI.url)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().registerTypeAdapter(new TypeToken<List<Race>>() {
            }.getType(), new RaceListDeserializer()).create())).build().create(IFormulaOneAPI.class);

}
