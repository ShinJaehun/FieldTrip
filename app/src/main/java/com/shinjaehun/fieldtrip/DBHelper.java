package com.shinjaehun.fieldtrip;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String TABLE_PLACES = "places";
    public static final String COLUMN_PLACE_ID = "_id";
    public static final String COLUMN_PLACE_TYPE = "type";
    public static final String COLUMN_PLACE_NAME = "name";
    public static final String COLUMN_PLACE_PIC = "pic";
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
            + COLUMN_PLACE_LOCATION + " TEXT NOT NULL, "
            + COLUMN_PLACE_DESCRIPTION + " TEXT NOT NULL, "
            + COLUMN_PLACE_DETAIL + " TEXT NOT NULL"
            + ");";

    private static DBHelper instance;
    private static SQLiteDatabase db;

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.i(TAG, "Create or Open database : " + DATABASE_NAME);
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        //이 생성자는 사용되지 않는 듯하다...
//        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
//    }

    private static void initialize(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
            try {
                Log.i(TAG, "Create or Open database : " + DATABASE_NAME);
                db = instance.getWritableDatabase();
            } catch (SQLException se) {
                Log.e(TAG, "Couldn't create or open the database " + DATABASE_NAME);
            }
            Log.i(TAG, "instance of database " + DATABASE_NAME + " created!");
        }
    }

    public static final DBHelper getInstance(Context context) {
        initialize(context);
        return instance;
    }

    public static final SQLiteDatabase getDb() {
        return db;
    }

    public void close() {
        if (instance != null) {
            Log.i(TAG, "Closing the database " + DATABASE_NAME);
            db.close();
            instance = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PLACES);
        //테이블 생성

        db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('1', 'history', '국립제주박물관', 'jejumuseum', 'geo:33.5140015,126.5467813?q=국립제주박물관', '국립제주박물관입니다.'," +
                " '국립제주박물관은 제주의 역사와 문화유산을 전시· 보존· 연구하는 고고·역사 박물관입니다. 제주의 여러 유적에서 출토된 유물과 역사적 문물들을 중심으로 선사시대부터 조선시대까지 각 유적과 유물이 갖는 역사·문화적 의의를 담은 전시품을 소개하고 있습니다.\n" +
                "\n" +
                "6개의 전시실로 이루어진 상설전시실에서는 제주 고유의 문화를 체계적으로 선보이고 있으며, 기획전시실에서는 해마다 다양한 주제의 특별전을 개최하고 있습니다.');");
        db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('2', 'people', '신재훈님네집', 'jehunshin', 'lat2, long2', '신재훈님네 집입니다.', '신재훈님께서 살고 계신 집이다.');");
        db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('3', 'history', '삼양동선사유적지', 'samyangprehistoric', 'lat3, long3', '삼양동 선사시대 유적지입니다.', '선사시대 사람들이 살던 유적지입니다.');");
        //테이블 초기화
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }
}
