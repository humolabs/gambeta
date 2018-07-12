package com.humolabs.gambeta.model;

import java.util.List;

public class Match {

    private String address;
    private String day;
    private String hour;
    private List<Player> players;

    public Match(String address, String day, String hour, List<Player> players) {
        this.address = address;
        this.day = day;
        this.hour = hour;
        this.players = players;
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
}
