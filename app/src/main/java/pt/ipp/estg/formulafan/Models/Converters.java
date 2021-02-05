package pt.ipp.estg.formulafan.Models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public String fromResultList(List<Result> results) {
        if (results == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Result>>() {
        }.getType();
        String json = gson.toJson(results, type);
        return json;
    }

    @TypeConverter
    public List<Result> toResultList(String resultsString) {
        if (resultsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Result>>() {
        }.getType();
        List<Result> results = gson.fromJson(resultsString, type);
        return results;
    }

}
