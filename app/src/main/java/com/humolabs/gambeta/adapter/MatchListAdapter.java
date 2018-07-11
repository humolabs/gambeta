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

    private List<Match> matches = new ArrayList<>();
    private Context context;

    public MatchListAdapter(@NonNull Context context, ArrayList<Match> list) {
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
            matchListView = LayoutInflater.from(context).inflate(R.layout.vista_contacto, parent, false);

        Match currentMatch = matches.get(position);

        TextView direccion = (TextView) matchListView.findViewById(R.id.direccion);
        direccion.setText(currentMatch.getAddress());

        TextView fechaHora = (TextView) matchListView.findViewById(R.id.fechahora);
        fechaHora.setText(currentMatch.getAddress());
        return matchListView;
    }
}
