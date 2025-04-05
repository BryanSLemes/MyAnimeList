package com.myanimelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "anime_db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ANIME = "anime";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";

    public static final String TABLE_ANIME_PENDENTE = "anime_pendente";
    public static final String COLUMN_PENDING_ID = "id";
    public static final String COLUMN_LINK = "link";

    private static final String SQL_CREATE_TABLE_ANIME =
            "CREATE TABLE " + TABLE_ANIME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_IMAGE + " BLOB);";

    private static final String SQL_CREATE_TABLE_ANIME_PENDENTE =
            "CREATE TABLE " + TABLE_ANIME_PENDENTE + " (" +
                    COLUMN_PENDING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LINK + " TEXT NOT NULL);";

    private static final String SQL_DELETE_TABLE_ANIME =
            "DROP TABLE IF EXISTS " + TABLE_ANIME;

    private static final String SQL_DELETE_TABLE_ANIME_PENDENTE =
            "DROP TABLE IF EXISTS " + TABLE_ANIME_PENDENTE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ANIME);
        db.execSQL(SQL_CREATE_TABLE_ANIME_PENDENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_ANIME);
        db.execSQL(SQL_DELETE_TABLE_ANIME_PENDENTE);
        onCreate(db);
    }
}
