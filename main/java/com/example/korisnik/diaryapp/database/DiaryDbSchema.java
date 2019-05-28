package com.example.korisnik.diaryapp.database;



public class DiaryDbSchema {
    public static final class DiaryTable{
        public static final String NAME = "stories";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String PRICA = "solved";

        }
    }
}
