package com.humolabs.gambeta.model;

import java.io.Serializable;
import java.util.List;

public class Match implements Serializable{

    private String key;
    private String address;
    private String day;
    private String hour;
    private List<Player> players;

    public Match (){
        // Default constructor required for calls to DataSnapshot.getValue(Match.class)
    }

    public Match(String address, String day, String hour, List<Player> players) {
        this.address = address;
        this.day = day;
        this.hour = hour;
        this.players = players;
    }

    public Match(String address, String day, String hour) {
        this.address = address;
        this.day = day;
        this.hour = hour;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    @Override
    public String toString() {
        return "Match{" +
                "key='" + key + '\'' +
                "address='" + address + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", players=" + players +
                '}';
    }
}
