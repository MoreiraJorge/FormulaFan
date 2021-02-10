package pt.ipp.estg.formulafan.Models;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

            //Setting Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = null;

            try {
                date = dateFormat.parse(itemJsonObject.get("date").getAsString());
            } catch (ParseException e) {
                date = Calendar.getInstance().getTime();
            }

            if (itemJsonObject.get("time") != null) {
                //Setting Time
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
                Time timeValue = null;

                try {
                    timeValue = new Time(timeFormat.parse(itemJsonObject.get("time").getAsString()).getTime());
                } catch (ParseException e) {
                    timeValue = null;
                }

                if (timeValue != null) {
                    date.setHours(timeValue.getHours());
                    date.setMinutes(timeValue.getMinutes());
                }
            }

            //Setting Circuit
            final JsonObject circuitJsonObject = itemJsonObject.get("Circuit").getAsJsonObject();
            final String circuitId = circuitJsonObject.get("circuitId").getAsString();
            final String circuitName = circuitJsonObject.get("circuitName").getAsString();
            final JsonObject locationJsonObject = circuitJsonObject.get("Location").getAsJsonObject();

            //Setting Location
            final double lat = Double.parseDouble(locationJsonObject.get("lat").getAsString());
            final double lng = Double.parseDouble(locationJsonObject.get("long").getAsString());
            final String locality = locationJsonObject.get("locality").getAsString();
            final String country = locationJsonObject.get("country").getAsString();

            Location tmpLocation = new Location(lat, lng, locality, country);

            final Circuit tmpCircuit = new Circuit(circuitId, circuitName, tmpLocation);

            raceList.add(new Race(season, round, raceName, tmpCircuit, date));
        }

        return raceList;
    }
}