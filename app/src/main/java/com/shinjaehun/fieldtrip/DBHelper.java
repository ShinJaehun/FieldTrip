package com.shinjaehun.fieldtrip;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String TABLE_PLACES = "places";
    public static final String COLUMN_PLACE_ID = "_id";
    public static final String COLUMN_PLACE_TYPE = "type";
    public static final String COLUMN_PLACE_NAME = "name";
    public static final String COLUMN_PLACE_PIC = "pic";
    public static final String COLUMN_PLACE_MAP = "map";
    public static final String COLUMN_PLACE_LOCATION = "location";
    public static final String COLUMN_PLACE_DESCRIPTION = "description";
    public static final String COLUMN_PLACE_DETAIL = "detail";

    private static final String DATABASE_NAME = "places.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_PLACES = "CREATE TABLE " + TABLE_PLACES + "("
            + COLUMN_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PLACE_TYPE + " TEXT NOT NULL, "
            + COLUMN_PLACE_NAME + " TEXT NOT NULL, "
            + COLUMN_PLACE_PIC + " TEXT NOT NULL, "
            + COLUMN_PLACE_MAP + " TEXT NOT NULL, "
            + COLUMN_PLACE_LOCATION + " TEXT NOT NULL, "
            + COLUMN_PLACE_DESCRIPTION + " TEXT NOT NULL, "
            + COLUMN_PLACE_DETAIL + " TEXT NOT NULL"
            + ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PLACES);
        db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('1', 'history', '국립제주박물관', 'jejumuseum', 'p1map.jpg', 'lat1, long1', '국립제주박물관입니다.', '국립제주박물관은 별로 볼건 없고... 세금 낭비인 곳이다.');");
        db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('2', 'people', '신재훈님네집', 'jehunshin', 'p2map.jpg', 'lat2, long2', '신재훈님네 집입니다.', '신재훈님께서 살고 계신 집이다.');");
        db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('3', 'people', '삼양동선사유적지', 'samyangprehistoric', 'p3map.jpg', 'lat3, long3', '삼양동 선사시대 유적지입니다.', '선사시대 사람들이 살던 유적지입니다.');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }
}
