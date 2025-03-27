package com.myanimelist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseManager {

    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addAnime(String name, String imagePath) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_IMAGE_PATH, imagePath);

        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Anime> getAllAnimeNames() {
        ArrayList<Anime> animeList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            animeList.add(new Anime(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
            ));
        }
        cursor.close();
        db.close();
        return animeList;
    }

    public boolean deleteAnimeById(int animeId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(animeId)};
        int deletedRows = db.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);

        db.close();

        if (deletedRows > 0) {
           return true;
        }

        return false;
    }
}
