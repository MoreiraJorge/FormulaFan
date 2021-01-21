package pt.ipp.estg.formulafan.Databases;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pt.ipp.estg.formulafan.Models.Circuit;
import pt.ipp.estg.formulafan.Models.Location;
import pt.ipp.estg.formulafan.Models.Race;

public class RaceListDeserializer implements JsonDeserializer<List<Race>> {
    @Override
    public List<Race> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Race> raceList = new ArrayList<>();

        JsonObject input = json.getAsJsonObject();
        JsonObject data = input.get("MRData").getAsJsonObject();
        JsonObject raceTable = data.get("RaceTable").getAsJsonObject();
        JsonArray races = raceTable.get("Races").getAsJsonArray();

        for (JsonElement itemsJsonElement : races) {
            final JsonObject itemJsonObject = itemsJsonElement.getAsJsonObject();

            final int season = Integer.parseInt(itemJsonObject.get("season").getAsString());
            final int round = Integer.parseInt(itemJsonObject.get("round").getAsString());
            final String raceName = itemJsonObject.get("raceName").getAsString();
            //Completar Parsing
            final Circuit circuit = new Circuit("", "", new Location(-10.0, -10.0, "", ""));
            final Date date = Calendar.getInstance().getTime();

            raceList.add(new Race(season, round, raceName, circuit, date));
        }

        return raceList;
    }
}