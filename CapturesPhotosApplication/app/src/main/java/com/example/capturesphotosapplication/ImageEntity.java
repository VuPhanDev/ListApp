package com.example.capturesphotosapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "images")
public class ImageEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private byte[] imageData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
