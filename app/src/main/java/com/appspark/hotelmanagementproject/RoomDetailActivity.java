package com.appspark.hotelmanagementproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RoomDetailActivity extends OptionMenuActivity {
    EditText etRoomNumber;
    EditText etRoomPrice;
    EditText etDescription;
    Spinner spnRoomType;
    CheckBox cbRoomStatus;
    String recordId;
    Button btnEditUpdate;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomdetail);
        etRoomNumber = (EditText) findViewById(R.id.etRoomDetailRoomNumber);
        etRoomPrice = (EditText) findViewById(R.id.etRoomDetailRoomPrice);
        etDescription = (EditText) findViewById(R.id.etRoomDetailDescription);
        spnRoomType = (Spinner) findViewById(R.id.spnRoomDetailRoomType);
        cbRoomStatus = (CheckBox) findViewById(R.id.cbRoomDetailStatus);

        setElementsState(false);

        Intent intent = this.getIntent();
        recordId = intent.getStringExtra("recordId");

        DBAdapter db = new DBAdapter(getApplicationContext());
        db.openDB();
        Cursor c = db.getRoomById(Long.parseLong(recordId));

        List<String> listRoomTypes = new ArrayList<String>();
        Cursor cRoomTypes = db.getAllRoomType();
        if (cRoomTypes.moveToFirst()) {
            do {
                listRoomTypes.add(cRoomTypes.getString(1));
            } while (cRoomTypes.moveToNext());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listRoomTypes);
        spnRoomType.setAdapter(arrayAdapter);

        if (c.moveToFirst()) {
            etRoomNumber.setText(c.getString(1));
            etRoomPrice.setText(c.getString(4));
            etDescription.setText(c.getString(5));
            if (c.getString(3).compareTo("Y") == 0) {
                cbRoomStatus.setChecked(true);
            } else {
                cbRoomStatus.setChecked(false);
            }
            spnRoomType.setSelection(arrayAdapter.getPosition(c.getString(2)));
        } else {
            Toast.makeText(getApplicationContext(), "An error occurs. Please try again later.", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        //setup button Edit/Update
        btnEditUpdate = (Button) findViewById(R.id.btnRoomDetailUpdate);
        btnEditUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((btnEditUpdate.getText().toString()).toUpperCase()).compareTo("EDIT") == 0) {
                    btnEditUpdate.setText("Update");
                    setElementsState(true);
                } else if (((btnEditUpdate.getText().toString()).toUpperCase()).compareTo("UPDATE") == 0) {
                    btnEditUpdate.setText("Edit");
                    setElementsState(false);

                    String cb;
                    if (cbRoomStatus.isChecked()) {
                        cb = "Y";
                    } else {
                        cb = "N";
                    }
                    DBAdapter mDB = new DBAdapter(getApplicationContext());
                    mDB.openDB();
                    mDB.updateRoom(Long.parseLong(recordId),
                            etRoomNumber.getText().toString(),
                            spnRoomType.getSelectedItem().toString(),
                            cb,
                            etRoomPrice.getText().toString(),
                            etDescription.getText().toString());
                    mDB.closeDB();
                    Toast.makeText(getApplicationContext(), "Room successfully updated.", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });

        //setup Delete button
        btnDelete = (Button) findViewById(R.id.btnRoomDetailDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAdapter mDB = new DBAdapter(getApplicationContext());
                mDB.openDB();
                mDB.deleteRoom(Long.parseLong(recordId));
                mDB.closeDB();
                Toast.makeText(getApplicationContext(), "Room successfully deleted.", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        db.closeDB();
    }

    public void setElementsState (boolean _yesno) {
        etRoomNumber.setEnabled(_yesno);
        etRoomPrice.setEnabled(_yesno);
        etDescription.setEnabled(_yesno);
        spnRoomType.setEnabled(_yesno);
        cbRoomStatus.setEnabled(_yesno);
    }
}
