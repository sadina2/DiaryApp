package com.example.korisnik.diaryapp.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.korisnik.diaryapp.database.DiaryDbSchema.DiaryTable;

public class DiaryBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "diaryBase.db";
    public DiaryBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DiaryDbSchema.DiaryTable.NAME +"(" +
                " _id integer primary key autoincrement, " +
                DiaryTable.Cols.UUID + ", " +
                DiaryTable.Cols.TITLE + ", " +
                DiaryTable.Cols.DATE + ", " +
                DiaryTable.Cols.PRICA +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

