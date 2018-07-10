package com.humolabs.gambeta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends ArrayAdapter<Match> implements View.OnClickListener {

    private List<Match> matches = new ArrayList<>();
    private Context context;

    public MatchListAdapter(@NonNull Context context, ArrayList<Match> list) {
        super(context, 0 , list);
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
        return super.getView(position, convertView, parent);
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vista_contacto, parent, false);
        }

        Match currentMatch = matches.get(position);
        return matchListView;
    }
}
