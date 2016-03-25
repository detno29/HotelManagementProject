package com.appspark.hotelmanagementproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends OptionMenuActivity {
    TextView tvTitle;
    ListView lvRooms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = (TextView) findViewById(R.id.tvMainActivityWelcomeText);
        lvRooms = (ListView) findViewById(R.id.lvMainActivityListView);

        DBAdapter db = new DBAdapter(getApplicationContext());
        db.openDB();

        //Check first time used
        Cursor cursor = db.getAllRoomType();
        if (cursor.getCount() == 0) {
            db.insertRoomType("Standard");
            db.insertRoomType("Deluxe");
            db.insertRoomType("Superior");
        }

        //Check if there is any room in the DB or not
        Cursor cursorRoom = db.getAllRooms();
        if (cursorRoom.getCount() > 0) {
            tvTitle.setVisibility(View.GONE);
            lvRooms.setVisibility(View.VISIBLE);

            //Load all rooms to the listview
            ListAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.custom_listview,
                    cursorRoom,
                    new String[]{"_id", "roomNumber", "roomType", "roomPrice"},
                    new int[]{R.id.tvCRoomId, R.id.tvCRoomNumber, R.id.tvCRoomType, R.id.tvCRoomPrice});
            lvRooms.setAdapter(adapter);

            lvRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String recordId = ((TextView) view.findViewById(R.id.tvCRoomId)).getText().toString();

                    Intent intent = new Intent(getApplicationContext(), RoomDetailActivity.class);
                    intent.putExtra("recordId", recordId);
                    startActivity(intent);
                }
            });
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            lvRooms.setVisibility(View.GONE);
        }

        db.closeDB();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewRoomActivity.class);
                startActivity(intent);
            }
        });
    }
}
