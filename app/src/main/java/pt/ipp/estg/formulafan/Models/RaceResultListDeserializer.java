package pt.ipp.estg.formulafan.Models;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RaceResultListDeserializer implements JsonDeserializer<List<RaceResult>> {
    @Override
    public List<RaceResult> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<RaceResult> raceResultList = new ArrayList<>();

        JsonObject input = json.getAsJsonObject();
        JsonObject data = input.get("MRData").getAsJsonObject();
        JsonObject raceTable = data.get("RaceTable").getAsJsonObject();
        JsonArray races = raceTable.get("Races").getAsJsonArray();

        for (JsonElement raceJsonElement : races) {
            final JsonObject raceJsonObject = raceJsonElement.getAsJsonObject();

            final int season = Integer.parseInt(raceJsonObject.get("season").getAsString());
            final int round = Integer.parseInt(raceJsonObject.get("round").getAsString());
            final String raceName = raceJsonObject.get("raceName").getAsString();

            JsonArray results = raceJsonObject.get("Results").getAsJsonArray();
            List<Result> resultList = new ArrayList<>();

            for (JsonElement resultJsonElement : results) {
                final JsonObject resultJsonObject = resultJsonElement.getAsJsonObject();

                //Position
                final int position = Integer.parseInt(resultJsonObject.get("position").getAsString());

                //Driver
                final JsonObject driverJsonObject = resultJsonObject.get("Driver").getAsJsonObject();
                final String driverName = driverJsonObject.get("givenName").getAsString() + " " + driverJsonObject.get("familyName").getAsString();

                //Team
                final JsonObject teamJsonObject = resultJsonObject.get("Constructor").getAsJsonObject();
                final String teamName = teamJsonObject.get("name").getAsString();

                Result tmp = new Result(position, driverName, teamName);
                resultList.add(tmp);
            }

            raceResultList.add(new RaceResult(season, round, raceName, resultList));
        }

        return raceResultList;
    }
}
