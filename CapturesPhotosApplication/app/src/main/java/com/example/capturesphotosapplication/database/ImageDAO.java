package com.example.capturesphotosapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.capturesphotosapplication.ImageEntity;

import java.util.List;

@Dao
public interface ImageDAO {
    @Insert
    void insertImage(ImageEntity imageEntity);

    @Query("SELECT * FROM images")
    List<ImageEntity>getListImages();
}
