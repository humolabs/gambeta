package com.humolabs.gambeta;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.humolabs.gambeta.adapter.MatchListAdapter;
import com.humolabs.gambeta.model.FruitData;
import com.humolabs.gambeta.model.Match;
import com.humolabs.gambeta.service.MatchServiceImplementation;
import com.humolabs.gambeta.viewmodel.MatchesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MatchServiceImplementation matchService;
    MatchListAdapter matchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        final ListView matchListView = findViewById(R.id.matchList);

        //Init data
        List<Match> matchList = FruitData.getMatches();
        matchService = new MatchServiceImplementation();
        matchService.saveMatches(matchList);

        matchListView.setAdapter(new MatchListAdapter(getApplicationContext(), matchService.getAll()));
        //Floating button init
        FloatingActionButton fabAdd = findViewById(R.id.btnAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchService.addRandomMatch();
            }
        });

        FloatingActionButton fabRemove = findViewById(R.id.btnRemoveAll);
        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchService.removeAll();
            }
        });

        MatchesViewModel viewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        viewModel.getMatches().observe(this, new Observer<List<Match>>() {
            @Override
            public void onChanged(@Nullable List<Match> matches) {
                matchListView.setAdapter(new MatchListAdapter(getApplicationContext(), matches));
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
