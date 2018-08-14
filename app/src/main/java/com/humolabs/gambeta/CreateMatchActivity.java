package com.humolabs.gambeta;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.humolabs.gambeta.model.FruitData;
import com.humolabs.gambeta.model.Match;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateMatchActivity extends AppCompatActivity {

    EditText inputAddress;
    EditText inputDateAndHour;
    ListView playerList;
    Button rightButton;
    Button leftButton;
    Button addPlayer;
    DatabaseReference refMatches;

    int mYear;
    int mMonth;
    int mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        refMatches = FirebaseDatabase.getInstance().getReference("matches");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_create_update_view);

        inputAddress = findViewById(R.id.input_field_address);
        inputDateAndHour = findViewById(R.id.input_field_date_hour);
        rightButton = findViewById(R.id.rightButton);
        leftButton = findViewById(R.id.leftButton);
        addPlayer = findViewById(R.id.addPlayer);

        playerList = findViewById(R.id.playerList);

        leftButton.setText(R.string.cancel);
        rightButton.setText(R.string.accept);

        inputDateAndHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(CreateMatchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        String format = "dd MMMM 'a las' hh:mm"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.forLanguageTag("es-ES"));
                        inputDateAndHour.setText(sdf.format(calendar.getTime()));

                        mDay = selectedDay;
                        mMonth = selectedMonth;
                        mYear = selectedYear;
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CreateMatchActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_player, null);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btnAccept = dialogView.findViewById(R.id.btnDialogAccept);
                Button btnCancel = dialogView.findViewById(R.id.btnDialogCancel);
                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CreateMatchActivity.this, "Aceptar", Toast.LENGTH_SHORT).show();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(CreateMatchActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

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
