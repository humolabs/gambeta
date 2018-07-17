package com.humolabs.gambeta.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.humolabs.gambeta.model.Match;
import com.humolabs.gambeta.service.MatchServiceImplementation;

import java.util.List;

public class MatchesViewModel extends ViewModel {

    private MutableLiveData<List<Match>> matches;
    private MatchServiceImplementation matchService;

    public LiveData<List<Match>> getMatches() {
        matchService = new MatchServiceImplementation();
        if (matches == null) {
            matches = new MutableLiveData<>();
            loadMatches();
        }
        return matches;
    }

    private void loadMatches() {
        matchService.getAll();
    }
}
