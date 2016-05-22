package com.shinjaehun.fieldtrip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class PlaceDAO {

    public static final String TAG = "PlaceDAO";


    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;
    private String[] allColumns = { DBHelper.COLUMN_PLACE_ID, DBHelper.COLUMN_PLACE_TYPE,
            DBHelper.COLUMN_PLACE_NAME, DBHelper.COLUMN_PLACE_PIC, DBHelper.COLUMN_PLACE_MAP,
            DBHelper.COLUMN_PLACE_LOCATION, DBHelper.COLUMN_PLACE_DESCRIPTION, DBHelper.COLUMN_PLACE_DETAIL };

    public PlaceDAO(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(context);

        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public Place createPlace(String type, String name, String pic, String map, String location, String description, String detail) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PLACE_TYPE, type);
        values.put(DBHelper.COLUMN_PLACE_NAME, name);
        values.put(DBHelper.COLUMN_PLACE_PIC, pic);
        values.put(DBHelper.COLUMN_PLACE_MAP, map);
        values.put(DBHelper.COLUMN_PLACE_LOCATION, location);
        values.put(DBHelper.COLUMN_PLACE_DESCRIPTION, description);
        values.put(DBHelper.COLUMN_PLACE_DETAIL, detail);

        long insertId = database.insert(DBHelper.TABLE_PLACES, null, values);
        Cursor cursor = database.query(DBHelper.TABLE_PLACES, allColumns,
                DBHelper.COLUMN_PLACE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Place newPlace = cursorToPlace(cursor);
        cursor.close();
        return newPlace;
    }

    public void deletePlace(Place place) {
        long id = place.getId();
        System.out.println("the deleted place has the id: " + id);
        database.delete(DBHelper.TABLE_PLACES, DBHelper.COLUMN_PLACE_ID + " = " + id, null);
    }

    public List<Place> getAllPlaces() {
        List<Place> listPlaces = new ArrayList<Place>();
        Cursor cursor = database.query(DBHelper.TABLE_PLACES, allColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Place place = cursorToPlace(cursor);
                listPlaces.add(place);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listPlaces;
    }

    public Place getPlaceById(long id) {
        Cursor cursor = database.query(DBHelper.TABLE_PLACES, allColumns,
                DBHelper.COLUMN_PLACE_ID + " = ",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Place place = cursorToPlace(cursor);
        return place;
    }

    protected Place cursorToPlace(Cursor cursor) {
        Place place = new Place();
        place.setId(cursor.getLong(0));
        place.setType(cursor.getString(1));
        place.setName(cursor.getString(2));
        place.setPic(cursor.getString(3));
        place.setMap(cursor.getString(4));
        place.setLocation(cursor.getString(5));
        place.setDescription(cursor.getString(6));
        place.setDetail(cursor.getString(7));
        return place;
    }

}
