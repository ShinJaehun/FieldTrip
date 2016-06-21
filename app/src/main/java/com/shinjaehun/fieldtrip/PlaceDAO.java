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
    private String[] allColumns = { DBHelper.COLUMN_PLACE_ID, DBHelper.COLUMN_PLACE_TYPE,
            DBHelper.COLUMN_PLACE_NAME, DBHelper.COLUMN_PLACE_PIC, DBHelper.COLUMN_PLACE_LOCATION,
            DBHelper.COLUMN_PLACE_DESCRIPTION, DBHelper.COLUMN_PLACE_DETAIL };

    public PlaceDAO(Context c) {
        dbHelper = DBHelper.getInstance(c);
        database = dbHelper.getDb();
//        this.context = c;
//        dbHelper = new DBHelper(context);
        //DBHelper 생성

//        try {
//            open();
//            //DBHelper를 통해 쓰기 가능한 database 객체 불러오기
//        } catch (SQLException e) {
//            Log.e(TAG, "SQLException on openning database " + e.getMessage());
//            e.printStackTrace();
//        }
    }

//    public void open() throws SQLException {
//        database = dbHelper.getWritableDatabase();
//    }

    public void close() {
        dbHelper.close();
    }

    public Place createPlace(String type, String name, String pic, String map, String location, String description, String detail) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PLACE_TYPE, type);
        values.put(DBHelper.COLUMN_PLACE_NAME, name);
        values.put(DBHelper.COLUMN_PLACE_PIC, pic);
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

    public List<Place> getPlacesByType(String type) {
        //유형에 따라 place 정렬하기
        List<Place> listPlaces = new ArrayList<Place>();
        Cursor cursor = database.query(DBHelper.TABLE_PLACES, allColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //cursor를 첫 row로 이동시킴
            while (!cursor.isAfterLast()) {
                Place place = cursorToPlace(cursor);
                //현재 cursor가 가리키는 place를 반환하는 함수

                if (place.getType().equals(type)) {
                    //place의 유형이 type과 동일할 때
                    listPlaces.add(place);
                    //listPlaces에 추가
                }
                cursor.moveToNext();
                //cursor를 다음 row로 이동시킴
            }
            cursor.close();
            //cursor 닫기
        }
        return listPlaces;
    }

    public Place getPlaceById(long id) {
        Cursor cursor = database.query(DBHelper.TABLE_PLACES, allColumns,
                DBHelper.COLUMN_PLACE_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        //이렇게 직접 query 가능, ID에 해당하는 cursor 리턴하기
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Place place = cursorToPlace(cursor);
        //cursor가 가리키는 place 리턴
        //cursor.close()로 종료해줘야 하는 것 아닌가?

        return place;
    }

    protected Place cursorToPlace(Cursor cursor) {
        Place place = new Place();
        place.setId(cursor.getLong(0));
        //cursor.get()은 DB 테이블에서 현재 cursor가 가리키는 data를 가져옴
        place.setType(cursor.getString(1));
        place.setName(cursor.getString(2));
        place.setPic(cursor.getString(3));
        place.setLocation(cursor.getString(4));
        place.setDescription(cursor.getString(5));
        place.setDetail(cursor.getString(6));
        return place;
    }

}
