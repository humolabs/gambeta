package com.humolabs.gambeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.humolabs.gambeta.model.FruitData;
import com.humolabs.gambeta.model.Match;

public class CreateMatchActivity extends AppCompatActivity {

    EditText inputAddress;
    EditText inputDateAndHour;
    Button rightButton;
    Button leftButton;
    DatabaseReference refMatches;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        refMatches = FirebaseDatabase.getInstance().getReference("matches");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_create_update_view);

        inputAddress = findViewById(R.id.input_field_address);
        inputDateAndHour = findViewById(R.id.input_field_date_hour);
        rightButton = findViewById(R.id.rightButton);
        leftButton = findViewById(R.id.leftButton);

        leftButton.setText(R.string.cancel);
        rightButton.setText(R.string.accept);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = inputAddress.getText().toString();
                String hour = inputDateAndHour.getText().toString();
                String day = "-" + inputDateAndHour.getText().toString();
                Match match = new Match(address, day, hour, FruitData.getPlayers());
                refMatches.push().setValue(match);
                Toast.makeText(CreateMatchActivity.this, "Created: " + match, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateMatchActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateMatchActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });
    }
}
