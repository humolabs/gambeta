package com.humolabs.gambeta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.humolabs.gambeta.R;
import com.humolabs.gambeta.model.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends ArrayAdapter<Match> {

    private List<Match> matches;
    private Context context;
    private DatabaseReference refMatches;

    public MatchListAdapter(@NonNull Context context, List<Match> list) {
        super(context, 0, list);
        this.context = context;
        matches = list;
        refMatches = FirebaseDatabase.getInstance().getReference("matches");
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View matchListView = convertView;
        if (convertView == null)
            matchListView = LayoutInflater.from(context).inflate(R.layout.match_view, parent, false);

        Match currentMatch = matches.get(position);

        TextView direccion = matchListView.findViewById(R.id.address);
        direccion.setText(currentMatch.getAddress());

        TextView fechaHora = matchListView.findViewById(R.id.dateandhour);
        fechaHora.setText(currentMatch.getHour());

        matchListView.findViewById(R.id.matchDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Match currentMatch = matches.get(position);
                refMatches.child(currentMatch.getKey()).setValue(null);
                matches.remove(position);
                notifyDataSetChanged();
            }
        });

        return matchListView;
    }
}
