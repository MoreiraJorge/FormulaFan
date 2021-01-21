package pt.ipp.estg.formulafan.Models;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(primaryKeys = {"lat", "lng"})
public class Location implements Serializable {

    public double lat;
    public double lng;
    public String locality;
    public String country;

    public Location(double lat, double lng, String locality, String country) {
        this.lat = lat;
        this.lng = lng;
        this.locality = locality;
        this.country = country;
    }
}
