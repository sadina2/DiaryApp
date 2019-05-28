package com.example.korisnik.diaryapp;

import com.example.korisnik.diaryapp.database.DiaryCursorWrapper;
import com.example.korisnik.diaryapp.database.DiaryDbSchema.DiaryTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.korisnik.diaryapp.database.DiaryBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class DiaryLab {

    private static DiaryLab sDiaryLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DiaryLab get(Context context) {
        if (sDiaryLab == null) {
            sDiaryLab = new DiaryLab(context);
        }
        return sDiaryLab;
    }
    private DiaryLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DiaryBaseHelper(mContext)
                .getWritableDatabase();
       // mDiary = new ArrayList<>();

    }
    public void addDiary(Diary c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(DiaryTable.NAME, null, values);
    }

    public List<Diary> getDiary(){
        List<Diary> stories = new ArrayList<>();
        DiaryCursorWrapper cursor = queryDiary(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                stories.add(cursor.getDiary());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return stories;
    }

    public Diary getDiary(UUID id) {
        DiaryCursorWrapper cursor = queryDiary(
                DiaryTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getDiary();
        } finally {
            cursor.close();
        }

    }

    public void updateDiary(Diary diary) {
        String uuidString = diary.getId().toString();
        ContentValues values = getContentValues(diary);

        mDatabase.update(DiaryTable.NAME, values,
                DiaryTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deleteStory(UUID storyId)
    {
        String uuidString = storyId.toString();

        mDatabase.delete(DiaryTable.NAME, DiaryTable.Cols.UUID + " = ?", new String[] {uuidString});
    }





    private DiaryCursorWrapper queryDiary(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DiaryTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new DiaryCursorWrapper(cursor);
    }


    private static ContentValues getContentValues(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(DiaryTable.Cols.UUID, diary.getId().toString());
        values.put(DiaryTable.Cols.TITLE, diary.getTitle());
        values.put(DiaryTable.Cols.DATE, diary.getDate().getTime());
        values.put(DiaryTable.Cols.PRICA, diary.getStory());

        return values;
    }





}
