package com.example.tuantase172217_petest2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tuantase172217_petest2.Model.Major;

public class MajorDB extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "MajorDB.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Major";
    private static final String COLUMN_ID = "idMajor";
    private static final String MAJOR_NAME = "name";

    public MajorDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MAJOR_NAME + " NVARCHAR(200));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor readAllMajor() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor allMajorData = null;
        if (db != null){
            allMajorData = db.rawQuery(query, null);
        }
        return allMajorData;
    }

    public static Cursor getData(Context context, String query) {
        MajorDB database = new MajorDB(context);
        return database.getReadableDatabase().rawQuery(query, null);
    }


    public void addMajor(String major_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MAJOR_NAME, major_name);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void updateMajor(int majorId, String majorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MAJOR_NAME, majorName);
        db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(majorId)});
        db.close();
    }

    public void deleteMajor(int majorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(majorId)});
        db.close();
    }

}
