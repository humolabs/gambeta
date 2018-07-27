package com.humolabs.gambeta.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.humolabs.gambeta.EditMatchActivity;
import com.humolabs.gambeta.R;
import com.humolabs.gambeta.model.Match;

import java.util.List;

public class MatchListAdapter extends ArrayAdapter<Match> {

    public static final String MATCH_SERIALIZABLE_INTENT = "match";

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
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {
        View matchListView = convertView;
        if (convertView == null)
            matchListView = LayoutInflater.from(context).inflate(R.layout.match_view, parent, false);

        Match currentMatch = matches.get(position);

        TextView address = matchListView.findViewById(R.id.address);
        address.setText(currentMatch.getAddress());

        TextView dateAndHour = matchListView.findViewById(R.id.dateandhour);
        dateAndHour.setText(currentMatch.getHour());

        matchListView.findViewById(R.id.matchDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Match currentMatch = matches.get(position);
                refMatches.child(currentMatch.getKey()).setValue(null);
                matches.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Removed: " + currentMatch, Toast.LENGTH_LONG).show();
            }
        });

        matchListView.findViewById(R.id.matchView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Match currentMatch = matches.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MATCH_SERIALIZABLE_INTENT, currentMatch);
                Intent intent = new Intent(context, EditMatchActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });

        return matchListView;
    }
}
