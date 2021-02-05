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

public class TeamPositionDeserializer implements JsonDeserializer<List<TeamPosition>> {
    @Override
    public List<TeamPosition> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<TeamPosition> teamPositionList = new ArrayList<>();

        JsonObject input = json.getAsJsonObject();
        JsonObject data = input.get("MRData").getAsJsonObject();
        JsonObject standingsTable = data.get("StandingsTable").getAsJsonObject();
        JsonArray standingsLists = standingsTable.get("StandingsLists").getAsJsonArray();
        JsonObject arrayObject = standingsLists.iterator().next().getAsJsonObject();
        JsonArray constructorStandings = arrayObject.get("ConstructorStandings").getAsJsonArray();

        for (JsonElement teamPositionJsonElement : constructorStandings) {
            final JsonObject teamPositionJsonObject = teamPositionJsonElement.getAsJsonObject();

            final int position = Integer.parseInt(teamPositionJsonObject.get("position").getAsString());
            final int points = Integer.parseInt(teamPositionJsonObject.get("points").getAsString());
            final int wins = Integer.parseInt(teamPositionJsonObject.get("wins").getAsString());

            JsonObject teamJsonObject = teamPositionJsonObject.get("Constructor").getAsJsonObject();
            final String constructorId = teamJsonObject.get("constructorId").getAsString();
            final String name = teamJsonObject.get("name").getAsString();
            final String teamNationality = teamJsonObject.get("nationality").getAsString();
            Team tmpTeam = new Team(constructorId, name, teamNationality);

            teamPositionList.add(new TeamPosition(position, tmpTeam, points, wins));
        }

        return teamPositionList;
    }
}
