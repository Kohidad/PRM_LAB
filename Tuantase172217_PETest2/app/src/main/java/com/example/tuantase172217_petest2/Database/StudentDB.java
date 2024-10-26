package com.example.tuantase172217_petest2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StudentDB extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "StudentDB.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Student";
    private static final String COLUMN_ID = "id";
    private static final String STUDENT_NAME = "name";
    private static final String DATE = "date";
    private static final String EMAIL = "email";
    private static final String GENDER = "gender";
    private static final String ADDRESS = "address";
    private static final String MAJOR_ID = "idMajor";


    public StudentDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STUDENT_NAME + " NVARCHAR(200), " +
                DATE + " TEXT, " +
                EMAIL + " NVARCHAR(200), " +
                GENDER + " NVARCHAR(200), " +
                ADDRESS + " NVARCHAR(200), " +
                MAJOR_ID + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor readAllStudent() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor allStudentData = null;
        if (db != null){
            allStudentData = db.rawQuery(query, null);
        }
        return allStudentData;
    }

    public void addStudent(String name, String date, String email, String gender, String address, int idMajor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_NAME, name);
        cv.put(DATE, date);
        cv.put(EMAIL, email);
        cv.put(GENDER, gender);
        cv.put(ADDRESS, address);
        cv.put(MAJOR_ID, idMajor);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public boolean updateStudent(int id, String name, String date, String email, String gender, String address, int majorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_NAME, name);
        cv.put(DATE, date);
        cv.put(EMAIL, email);
        cv.put(GENDER, gender);
        cv.put(ADDRESS, address);
        cv.put(MAJOR_ID, majorId);

        int rowsAffected = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }
}
