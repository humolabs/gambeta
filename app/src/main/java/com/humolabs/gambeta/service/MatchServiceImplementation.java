package com.humolabs.gambeta.service;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.humolabs.gambeta.model.FruitData;
import com.humolabs.gambeta.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class MatchServiceImplementation {

    private DatabaseReference matchesRef;
    private DatabaseReference ref;

    public MatchServiceImplementation(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("gambetapp");
        matchesRef = ref.child("matches");
    }

    public void saveMatch(Match match){
        Log.d(TAG, "Value is: " + match);
        matchesRef.push().setValue(match);
    }

    public void saveMatches(List<Match> matchList){
        for(Match match : matchList){
            Log.d(TAG, "Value is: " + match);
            matchesRef.push().setValue(match);
        }
    }

    public List<Match> getAll(){
        final List<Match> matches = new ArrayList<>();
        matchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot matchSnapshot: dataSnapshot.getChildren()) {
                    Match match = matchSnapshot.getValue(Match.class);
                    Log.d(TAG, "Value is: " + match);
                    matches.add(match);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return matches;
    }

    public void addRandomMatch() {
        saveMatch(new Match(UUID.randomUUID().toString(),  UUID.randomUUID().toString(), UUID.randomUUID().toString(), FruitData.getPlayers()));
    }

    public void removeAll() {
        matchesRef.setValue(null);
    }
}
