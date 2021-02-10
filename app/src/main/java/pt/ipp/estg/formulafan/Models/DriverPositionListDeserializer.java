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

public class DriverPositionListDeserializer implements JsonDeserializer<List<DriverPosition>> {
    @Override
    public List<DriverPosition> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<DriverPosition> driverPositionList = new ArrayList<>();

        JsonObject input = json.getAsJsonObject();
        JsonObject data = input.get("MRData").getAsJsonObject();
        JsonObject standingsTable = data.get("StandingsTable").getAsJsonObject();
        JsonArray standingsLists = standingsTable.get("StandingsLists").getAsJsonArray();
        JsonObject arrayObject = standingsLists.iterator().next().getAsJsonObject();
        JsonArray driverStandings = arrayObject.get("DriverStandings").getAsJsonArray();

        for (JsonElement driverPositionJsonElement : driverStandings) {
            final JsonObject driverPositionJsonObject = driverPositionJsonElement.getAsJsonObject();

            final int position = Integer.parseInt(driverPositionJsonObject.get("position").getAsString());
            final int points = Integer.parseInt(driverPositionJsonObject.get("points").getAsString());
            final int wins = Integer.parseInt(driverPositionJsonObject.get("wins").getAsString());

            JsonObject driver = driverPositionJsonObject.get("Driver").getAsJsonObject();
            final String driverId = driver.get("driverId").getAsString();
            final int permanentNumber = Integer.parseInt(driver.get("permanentNumber").getAsString());
            final String code = driver.get("code").getAsString();
            final String givenName = driver.get("givenName").getAsString();
            final String familyName = driver.get("familyName").getAsString();
            final String dateOfBirth = driver.get("dateOfBirth").getAsString();
            final String nationality = driver.get("nationality").getAsString();
            Driver tmpDriver = new Driver(driverId, permanentNumber, code, givenName, familyName, dateOfBirth, nationality);

            JsonArray team = driverPositionJsonObject.get("Constructors").getAsJsonArray();
            JsonObject arrayObjectTeam = team.iterator().next().getAsJsonObject();
            final String constructorId = arrayObjectTeam.get("constructorId").getAsString();
            final String name = arrayObjectTeam.get("name").getAsString();
            final String teamNationality = arrayObjectTeam.get("nationality").getAsString();
            Team tmpTeam = new Team(constructorId, name, teamNationality);

            driverPositionList.add(new DriverPosition(position, tmpDriver, tmpTeam, points, wins));
        }

        return driverPositionList;
    }
}
