package pt.ipp.estg.formulafan.Services;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import pt.ipp.estg.formulafan.Models.TeamPosition;
import pt.ipp.estg.formulafan.Models.TeamPositionDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeamPositionDetailsAPI {

    public static ITeamPositionDetailsAPI teamPositionDetailsAPI = new Retrofit.Builder().baseUrl(ITeamPositionDetailsAPI.url)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().registerTypeAdapter(new TypeToken<List<TeamPosition>>() {
            }.getType(), new TeamPositionDeserializer()).create())).build().create(ITeamPositionDetailsAPI.class);

}
