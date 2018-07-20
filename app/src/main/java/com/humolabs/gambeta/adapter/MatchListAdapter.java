package com.humolabs.gambeta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.humolabs.gambeta.R;
import com.humolabs.gambeta.model.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends ArrayAdapter<Match> implements View.OnClickListener {

    private List<Match> matches;
    private Context context;

    public MatchListAdapter(@NonNull Context context, List<Match> list) {
        super(context, 0, list);
        this.context = context;
        matches = list;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Match match = (Match) object;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View matchListView = convertView;
        if (convertView == null)
            matchListView = LayoutInflater.from(context).inflate(R.layout.match_view, parent, false);

        Match currentMatch = matches.get(position);

        TextView direccion = matchListView.findViewById(R.id.address);
        direccion.setText(currentMatch.getAddress());

        TextView fechaHora = matchListView.findViewById(R.id.dateandhour);
        fechaHora.setText(currentMatch.getHour());
        return matchListView;
    }
}
