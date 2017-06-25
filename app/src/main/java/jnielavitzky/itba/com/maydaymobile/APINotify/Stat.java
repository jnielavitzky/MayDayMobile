package jnielavitzky.itba.com.maydaymobile.APINotify;

/**
 * Created by Cappa on 25/6/2017.
 */

public class Stat {

    private String id;
    private String number;
    private Airline airline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
