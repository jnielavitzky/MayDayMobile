package jnielavitzky.itba.com.maydaymobile.APINotify;

/**
 * Created by Cappa on 25/6/2017.
 */

public class Status {
    private String id;
    private String number;
    private Airline airline;
    private String status;
    private Departure departure;
    private Departure arrival;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Departure getArrival() {
        return arrival;
    }

    public void setArrival(Departure arrival) {
        this.arrival = arrival;
    }
}
