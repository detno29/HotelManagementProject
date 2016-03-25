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

public class AddNewRoomActivity extends OptionMenuActivity {
    Spinner spnRoomType;
    Button btnAdd;
    EditText etRoomNumber;
    CheckBox cbRoomStatus;
    EditText etPrice;
    EditText etDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewroom);
        etDescription = (EditText) findViewById(R.id.etAddRoomDescription);
        etRoomNumber = (EditText) findViewById(R.id.etAddRoomRoomNumber);
        etPrice = (EditText) findViewById(R.id.etAddRoomRoomPrice);
        cbRoomStatus =  (CheckBox) findViewById(R.id.cbAddRoomStatus);

        //setup spinner
        spnRoomType = (Spinner) findViewById(R.id.spnAddRoomRoomType);
        List<String> roomTypes = new ArrayList<String>();
        DBAdapter db = new DBAdapter(getApplicationContext());
        db.openDB();
        Cursor c = db.getAllRoomType();
        if (c.moveToFirst()) {
            do {
                roomTypes.add(c.getString(1));
            } while (c.moveToNext());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomTypes);
        spnRoomType.setAdapter(arrayAdapter);
        db.closeDB();

        //setup add button
        btnAdd = (Button) findViewById(R.id.btnAddRoomAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cb;
                if (cbRoomStatus.isChecked()) {
                    cb = "Y";
                } else {
                    cb = "N";
                }

                DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
                dbAdapter.openDB();
                dbAdapter.insertRoom(etRoomNumber.getText().toString(),
                        spnRoomType.getSelectedItem().toString(),
                        cb,
                        etPrice.getText().toString(),
                        etDescription.getText().toString());
                dbAdapter.closeDB();

                Toast.makeText(getApplicationContext(), "Room added successfully.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
