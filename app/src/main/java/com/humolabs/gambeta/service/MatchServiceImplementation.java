package com.humolabs.gambeta.service;


import com.humolabs.gambeta.model.Match;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Component;

@Component
public class MatchServiceImplementation {

    public ArrayList<Match> getMatches(){
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(new Match("Algun lugar de Moreno 9123", "La canchota"));
        matches.add(new Match("Algun lugar de Moreno 12312", "La concha de tu madre"));
        return matches;
    }
}
