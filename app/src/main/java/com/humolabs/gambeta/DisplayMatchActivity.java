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
import com.humolabs.gambeta.adapter.MatchListAdapter;
import com.humolabs.gambeta.model.Match;

public class DisplayMatchActivity extends AppCompatActivity {

    EditText inputAddress;
    EditText inputDateAndHour;
    Button actionButton;
    Button editButton;
    DatabaseReference refMatches;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        refMatches = FirebaseDatabase.getInstance().getReference("matches");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_create_view);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final Match match = (Match) bundle.get(MatchListAdapter.MATCH_SERIALIZABLE_INTENT);

            inputAddress = findViewById(R.id.input_field_address);
            inputAddress.setText(match.getAddress());
            inputAddress.setEnabled(false);

            inputDateAndHour = findViewById(R.id.input_field_date_hour);
            inputDateAndHour.setText(match.getHour() + " || " + match.getHour());
            inputDateAndHour.setEnabled(false);

            actionButton = findViewById(R.id.actionButton);
            actionButton.setText(R.string.cancel);
            actionButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(DisplayMatchActivity.this, "Back", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DisplayMatchActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            editButton = findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    inputAddress.setEnabled(true);
                    inputDateAndHour.setEnabled(true);
                    editButton.setEnabled(false);

                    actionButton.setText(R.string.accept);
                    actionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DisplayMatchActivity.this, "Saving", Toast.LENGTH_LONG).show();
                            match.setAddress(inputAddress.getText().toString());
                            match.setHour(inputDateAndHour.getText().toString());
                            match.setDay("lo terminaré o no");
                            refMatches.child(match.getKey()).setValue(match);

                            Intent intent = new Intent(DisplayMatchActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }
}
