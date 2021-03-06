package com.humolabs.gambeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.humolabs.gambeta.adapter.MatchListAdapter;
import com.humolabs.gambeta.adapter.PlayerAdapter;
import com.humolabs.gambeta.model.Match;
import com.humolabs.gambeta.model.Player;

import java.util.ArrayList;

public class EditMatchActivity extends AppCompatActivity {

    EditText inputAddress;
    EditText inputDateAndHour;
    ListView playerList;
    Button rightButton;
    Button leftButton;
    DatabaseReference refMatches;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        refMatches = FirebaseDatabase.getInstance().getReference("matches");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_create_update_view);
        Bundle bundle = getIntent().getExtras();

        inputAddress = findViewById(R.id.input_field_address);
        inputDateAndHour = findViewById(R.id.input_field_date_hour);
        rightButton = findViewById(R.id.rightButton);
        leftButton = findViewById(R.id.leftButton);
        playerList = findViewById(R.id.playerList);

        final Match match = (Match) bundle.get(MatchListAdapter.MATCH_SERIALIZABLE_INTENT);

        PlayerAdapter adapter = new PlayerAdapter(this, (ArrayList<Player>) match.getPlayers());
        playerList.setAdapter(adapter);
        inputAddress.setText(match.getAddress());
        inputAddress.setEnabled(false);

        inputDateAndHour.setText(match.getHour() + " || " + match.getHour());
        inputDateAndHour.setEnabled(false);

        rightButton.setText(R.string.cancel);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditMatchActivity.this, "Back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditMatchActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputAddress.setEnabled(true);
                inputDateAndHour.setEnabled(true);

                leftButton.setText(R.string.accept);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EditMatchActivity.this, "Saving", Toast.LENGTH_LONG).show();
                        match.setAddress(inputAddress.getText().toString());
                        match.setHour(inputDateAndHour.getText().toString());
                        match.setDay("lo terminaré o no");

                        refMatches.child(match.getKey()).setValue(match);

                        Intent intent = new Intent(EditMatchActivity.this, AuthActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
