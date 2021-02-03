package pt.ipp.estg.formulafan.Models;

import androidx.room.Entity;
import androidx.room.Index;

import java.io.Serializable;

@Entity(primaryKeys = {"driverId"}, indices = {@Index(value = {"driverId"}, unique = true)})
public class Driver implements Serializable {

    public String driverId;
    public int permanentNumber;
    public String code;
    public String givenName;
    public String familyName;
    public String dateOfBirth;
    public String nationality;

    public Driver(String driverId, int permanentNumber, String code, String givenName, String familyName, String dateOfBirth, String nationality) {
        this.driverId = driverId;
        this.permanentNumber = permanentNumber;
        this.code = code;
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }
}
