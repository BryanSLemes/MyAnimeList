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

    public void addAnime(String name, byte[] image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_IMAGE, image);

        db.insert(DatabaseHelper.TABLE_ANIME, null, values);
        db.close();
    }

    public void addPendentAnime(String link) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LINK, link);

        db.insert(DatabaseHelper.TABLE_ANIME_PENDENTE, null, values);
        db.close();
    }

    public ArrayList<Anime> getAllAnimeNames() {
        ArrayList<Anime> animeList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_IMAGE
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ANIME,
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

    public ArrayList<Anime> getAllPendentAnimeNames() {
        ArrayList<Anime> animeList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_PENDING_ID,
                DatabaseHelper.COLUMN_LINK,
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ANIME_PENDENTE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            animeList.add(new Anime(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PENDING_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LINK))
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
        int deletedRows = db.delete(DatabaseHelper.TABLE_ANIME, selection, selectionArgs);

        db.close();

        if (deletedRows > 0) {
           return true;
        }

        return false;
    }

    public void updateImageInDatabase(int id, byte[] imageBytes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", imageBytes);

        db.update(DatabaseHelper.TABLE_ANIME, contentValues, "id = ?", new String[]{String.valueOf(id)});

        db.close();
    }
}
