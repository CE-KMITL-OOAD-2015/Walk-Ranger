package com.example.nattachai.walkingranger;

/**
 * Created by Vincent on 18/11/2558.
 */
public class Event {

    private String eventCode;
    private String eventName;
    private String description;
    private double location_lat;
    private double location_long;
    private String start;
    private String until;
    private int point;
    private boolean canDo;

    public Event() {
    }

    public String getEventCode() {

        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_long() {
        return location_long;
    }

    public void setLocation_long(double location_long) {
        this.location_long = location_long;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isCanDo() {
        return canDo;
    }

    public void setCanDo(boolean canDo) {
        this.canDo = canDo;
    }
}
