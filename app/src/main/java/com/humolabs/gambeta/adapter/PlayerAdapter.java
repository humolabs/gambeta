package com.humolabs.gambeta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.humolabs.gambeta.R;
import com.humolabs.gambeta.model.Player;

import java.util.ArrayList;
import java.util.Locale;

public class PlayerAdapter extends ArrayAdapter<Player> {

    public PlayerAdapter(@NonNull Context context, ArrayList<Player> playerArrayList) {
        super(context, 0, playerArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Player player = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.player_view, parent, false);
        }

        TextView nickName = convertView.findViewById(R.id.nickName);
        TextView name = convertView.findViewById(R.id.fullName);
        TextView contactPhone = convertView.findViewById(R.id.contactPhone);

        if (player != null) {
            nickName.setText(player.getNickName());
            name.setText(player.getName());
            if (player.getContactPhone() != null) {
                contactPhone.setText(String.format(Locale.ENGLISH, "%d", player.getContactPhone()));
            } else {
                contactPhone.setText(null);
            }
        }

        return convertView;
    }
}
