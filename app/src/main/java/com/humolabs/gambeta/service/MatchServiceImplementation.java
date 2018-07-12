package com.humolabs.gambeta.service;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.humolabs.gambeta.model.Match;
import com.humolabs.gambeta.model.Player;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MatchServiceImplementation {

    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public void save(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("matches");
        myRef.setValue(getMatches());
    }

    public void readData(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public ArrayList<Match> getMatches(){
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(new Match("Algun lugar de moreno", "Mañana", "15:20", getPlayers()));
        matches.add(new Match("Algun lugar de podesta", "Pasado", "17:20", getPlayers()));
        return matches;
    }

    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Jose", "El gordo", 1234567890));
        players.add(new Player("Enrique", "Comeviejas", 1234567890));
        players.add(new Player("Nahuel", "Chino", 1234567890));
        return players;
    }
}
