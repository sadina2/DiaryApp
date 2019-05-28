package com.example.korisnik.diaryapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.korisnik.diaryapp.database.DiaryDbSchema.DiaryTable;
import com.example.korisnik.diaryapp.Diary;

import java.util.Date;
import java.util.UUID;


public class DiaryCursorWrapper extends CursorWrapper {
    public DiaryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Diary getDiary() {
        String uuidString = getString(getColumnIndex(DiaryTable.Cols.UUID));
        String title = getString(getColumnIndex(DiaryTable.Cols.TITLE));
        long date = getLong(getColumnIndex(DiaryTable.Cols.DATE));
        String story = getString(getColumnIndex(DiaryTable.Cols.PRICA));

        Diary diary = new Diary(UUID.fromString(uuidString));

        diary.setTitle(title);
        diary.setDate(new Date(date));
        diary.setStory(story);

        return diary;
    }
}
