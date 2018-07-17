package com.humolabs.gambeta.model;

import java.util.ArrayList;
import java.util.List;

public class FruitData {
    public static List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Jose", "El gordo", 1234567890));
        players.add(new Player("Enrique", "Comeviejas", 1234567890));
        players.add(new Player("Nahuel", "Chino", 1234567890));
        return players;
    }

    public static ArrayList<Match> getMatches(){
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(new Match("Algun lugar de moreno", "Mañana", "15:20", getPlayers()));
        matches.add(new Match("Algun lugar de podesta", "Pasado", "17:20", getPlayers()));
        return matches;
    }
}
