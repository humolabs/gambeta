package com.humolabs.gambeta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.humolabs.gambeta.adapter.MatchListAdapter;
import com.humolabs.gambeta.model.Match;

public class DisplayMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_create_view);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Match match = (Match) bundle.get(MatchListAdapter.MATCH_SERIALIZABLE_INTENT);

            EditText inputAddress = findViewById(R.id.input_field_address);
            inputAddress.setText(match.getAddress());

            EditText inputDateAndHour = findViewById(R.id.input_field_date_hour);
            inputDateAndHour.setText(match.getHour() + " || " + match.getHour());
            if (!bundle.getBoolean("create")) {
                inputAddress.setEnabled(false);
                inputDateAndHour.setEnabled(false);
            }
        }

    }
}
