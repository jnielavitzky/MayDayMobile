package jnielavitzky.itba.com.maydaymobile.APINotify;

import java.util.Calendar;

/**
 * Created by Cappa on 25/6/2017.
 */

public class Departure {

    private Airport airport;
    private Calendar scheduled_time;
    private Calendar actual_time;
    private Calendar scheduled_gate_time;
    private Calendar actual_gate_time;
    private String   gate_delay;
    private Calendar estimate_runway_time;
    private Calendar actual_runway_time;
    private String   runway_delay;

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Calendar getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(Calendar scheduled_time) {
        this.scheduled_time = scheduled_time;
    }

    public Calendar getActual_time() {
        return actual_time;
    }

    public void setActual_time(Calendar actual_time) {
        this.actual_time = actual_time;
    }

    public Calendar getScheduled_gate_time() {
        return scheduled_gate_time;
    }

    public void setScheduled_gate_time(Calendar scheduled_gate_time) {
        this.scheduled_gate_time = scheduled_gate_time;
    }

    public Calendar getActual_gate_time() {
        return actual_gate_time;
    }

    public void setActual_gate_time(Calendar actual_gate_time) {
        this.actual_gate_time = actual_gate_time;
    }

    public String getGate_delay() {
        return gate_delay;
    }

    public void setGate_delay(String gate_delay) {
        this.gate_delay = gate_delay;
    }

    public Calendar getEstimate_runway_time() {
        return estimate_runway_time;
    }

    public void setEstimate_runway_time(Calendar estimate_runway_time) {
        this.estimate_runway_time = estimate_runway_time;
    }

    public Calendar getActual_runway_time() {
        return actual_runway_time;
    }

    public void setActual_runway_time(Calendar actual_runway_time) {
        this.actual_runway_time = actual_runway_time;
    }

    public String getRunway_delay() {
        return runway_delay;
    }

    public void setRunway_delay(String runway_delay) {
        this.runway_delay = runway_delay;
    }
}
