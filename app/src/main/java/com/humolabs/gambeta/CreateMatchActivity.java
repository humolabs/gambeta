package com.humolabs.gambeta;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.humolabs.gambeta.adapter.PlayerAdapter;
import com.humolabs.gambeta.model.FruitData;
import com.humolabs.gambeta.model.Match;
import com.humolabs.gambeta.model.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateMatchActivity extends AppCompatActivity {

    private static final String TAG = CreateMatchActivity.class.getName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    EditText inputAddress;
    EditText inputDateAndHour;
    ListView playerList;
    Button rightButton;
    Button leftButton;
    Button addPlayer;
    DatabaseReference refMatches;

    List<Player> players;
    PlayerAdapter adapter;

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
        players = new ArrayList<>();
        adapter = new PlayerAdapter(this, (ArrayList<Player>) players);
        playerList.setAdapter(adapter);

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
                final View dialogView = inflater.inflate(R.layout.dialog_add_player, null);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btnAccept = dialogView.findViewById(R.id.btnDialogAccept);
                Button btnCancel = dialogView.findViewById(R.id.btnDialogCancel);
                Button addFromContacts = dialogView.findViewById(R.id.btnAddFromContacts);
                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText inputNickName = dialogView.findViewById(R.id.inputNickName);
                        EditText inputName = dialogView.findViewById(R.id.inputName);
                        EditText inputContactNumber = dialogView.findViewById(R.id.inputContactNumber);

                        players.add(new Player(inputName.getText().toString(), inputNickName.getText().toString(), Integer.parseInt(inputContactNumber.getText().toString())));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(CreateMatchActivity.this, "Aceptar", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(CreateMatchActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                addFromContacts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(CreateMatchActivity.this,
                                Manifest.permission.READ_CONTACTS)
                                != PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(CreateMatchActivity.this,
                                    Manifest.permission.READ_CONTACTS)) {

                                // Show an expanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.

                            } else {

                                // No explanation needed, we can request the permission.

                                ActivityCompat.requestPermissions(CreateMatchActivity.this,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        }else{
                            getContactList();
                        }
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

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}

