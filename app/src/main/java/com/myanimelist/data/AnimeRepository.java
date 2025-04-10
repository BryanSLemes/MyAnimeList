package com.myanimelist.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AnimeRepository {

    @Query("SELECT * FROM AnimeEntity WHERE nome = :nome LIMIT 1")
    AnimeEntity findByName(String nome);

    @Query("SELECT * FROM AnimeEntity")
    List<AnimeEntity> getAll();

    @Insert
    void insert(AnimeEntity anime);

    @Delete
    void delete(AnimeEntity anime);

    @Update
    void update(AnimeEntity anime);
}
