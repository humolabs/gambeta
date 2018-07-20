package com.humolabs.gambeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.humolabs.gambeta.adapter.MatchListAdapter;
import com.humolabs.gambeta.model.FruitData;
import com.humolabs.gambeta.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    DatabaseReference refMatches;
    ImageView removeItem;

    ListView matchesListView;

    List<Match> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        refMatches = FirebaseDatabase.getInstance().getReference("matches");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        matchesListView = findViewById(R.id.matchList);
        removeItem = findViewById(R.id.matchDelete);

        matches = new ArrayList<>();
        //Floating button init
        FloatingActionButton fabAdd = findViewById(R.id.btnAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refMatches.push().setValue(
                        new Match(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), FruitData.getPlayers()));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refMatches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                matches.clear();
                for (DataSnapshot matchSnapshot : dataSnapshot.getChildren()) {
                    Match match = matchSnapshot.getValue(Match.class);
                    if (match != null) {
                        match.setKey(matchSnapshot.getKey());
                    }
                    matches.add(match);
                }
                MatchListAdapter matchListAdapter = new MatchListAdapter(MainActivity.this, matches);
                matchesListView.setAdapter(matchListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
