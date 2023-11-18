package com.example.capturesphotosapplication.database;

import android.content.Context;
import android.media.Image;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.capturesphotosapplication.ImageEntity;

@Database(entities = {ImageEntity.class}, version = 1)
public abstract class ImageDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "images.database";

    private static ImageDatabase instance;

    public static synchronized ImageDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ImageDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ImageDAO imageDAO();
}
