package com.appspark.hotelmanagementproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    private static final String TAG = "DBAdapter";

    public static final String ROOM_TYPE_ID = "_id";
    public static final String ROOM_TYPE_NAME = "roomTypeName";

    public static final String ROOM_ID = "_id";
    public static final String ROOM_NUMBER = "roomNumber";
    public static final String ROOM_TYPE = "roomType";
    public static final String ROOM_STATUS = "roomStatus";
    public static final String ROOM_PRICE = "roomPrice";
    public static final String ROOM_DESCRIPTION = "roomDescription";

    private static final String DB_NAME = "HotelManagementDB";
    private static final String DB_TABLE_ROOM_TYPE = "RoomTypeTable";
    private static final String DB_TABLE_ROOM = "RoomTable";
    private static final int DB_VERSION = 2;

    private static final String DB_CREATE_TABLE_ROOM_TYPE =
            "create table RoomTypeTable (_id integer primary key autoincrement, "
                    + "roomTypeName text);";

    private static final String DB_CREATE_TABLE_ROOM =
            "create table RoomTable (_id integer primary key autoincrement, "
                    + "roomNumber text, "
                    + "roomType text, "
                    + "roomStatus text, "
                    + "roomPrice text, "
                    + "roomDescription text);";

    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper mDBHelper;

    public DBAdapter(Context _context){
        this.context = _context;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context _context) {
            super(_context, DB_NAME, null, DB_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try {
                db.execSQL(DB_CREATE_TABLE_ROOM);
                db.execSQL(DB_CREATE_TABLE_ROOM_TYPE);
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_ROOM);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_ROOM_TYPE);
            onCreate(db);
        }

    }

    public DBAdapter openDB() throws SQLException
    {
        Log.w(TAG, "DB is opening....");
        mDBHelper = new DatabaseHelper(context);
        db = mDBHelper.getWritableDatabase();
        return this;
    }

    public void closeDB()
    {
        Log.w(TAG, "DB is closing....");
        db.close();
    }

    public long insertRoomType(String _roomTypeName){
        ContentValues cv = new ContentValues();
        cv.put(ROOM_TYPE_NAME, _roomTypeName);
        return db.insert(DB_TABLE_ROOM_TYPE, null, cv);
    }

    public Cursor getAllRoomType(){
        return db.query(DB_TABLE_ROOM_TYPE, new String[]{ROOM_TYPE_ID, ROOM_TYPE_NAME},
                null, null, null, null, null);
    }

    public long insertRoom(String _roomNumber, String _roomType, String _roomStatus, String _roomPrice, String _description){
        ContentValues cv = new ContentValues();
        cv.put(ROOM_NUMBER, _roomNumber);
        cv.put(ROOM_TYPE, _roomType);
        cv.put(ROOM_STATUS, _roomStatus);
        cv.put(ROOM_PRICE, _roomPrice);
        cv.put(ROOM_DESCRIPTION, _description);
        return db.insert(DB_TABLE_ROOM, null, cv);
    }

    public boolean updateRoom(long _id, String _roomNumber, String _roomType, String _roomStatus, String _roomPrice, String _description){
        ContentValues cv = new ContentValues();
        cv.put(ROOM_ID, _id);
        cv.put(ROOM_NUMBER, _roomNumber);
        cv.put(ROOM_TYPE, _roomType);
        cv.put(ROOM_STATUS, _roomStatus);
        cv.put(ROOM_PRICE, _roomPrice);
        cv.put(ROOM_DESCRIPTION, _description);
        return db.update(DB_TABLE_ROOM, cv, ROOM_ID + " = " + _id, null) > 0;
    }

    public boolean deleteRoom(long _id) {
        return db.delete(DB_TABLE_ROOM, ROOM_ID + " = " + _id, null) > 0;
    }

    public Cursor getAllRooms(){
        return db.query(DB_TABLE_ROOM, new String[]{ROOM_ID, ROOM_NUMBER, ROOM_TYPE, ROOM_STATUS, ROOM_PRICE, ROOM_DESCRIPTION},
                null, null, null, null, null);
    }

    public Cursor getRoomById(long _id){
        Cursor c = db.query(DB_TABLE_ROOM, new String[]{ROOM_ID, ROOM_NUMBER, ROOM_TYPE, ROOM_STATUS, ROOM_PRICE, ROOM_DESCRIPTION},
                ROOM_ID + " = " + _id,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    /*
    public boolean updateLightStatus(long _id, String _lightStatus){
        ContentValues cv = new ContentValues();
        cv.put(LIGHT_ID, _id);
        cv.put(LIGHT_STATUS, _lightStatus);
        return db.update(DB_TABLE, cv, LIGHT_ID + " = " + _id, null) > 0;
    }

    public Cursor getLightStatus(long _id){
        Cursor c = db.query(DB_TABLE, new String[]{LIGHT_ID, LIGHT_STATUS},
                LIGHT_ID + " = " + _id,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAll(){
        return db.query(DB_TABLE, new String[]{LIGHT_ID, LIGHT_STATUS},
                null, null, null, null, null);
    }
    */
}
