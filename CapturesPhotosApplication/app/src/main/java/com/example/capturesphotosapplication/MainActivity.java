package com.example.capturesphotosapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import com.example.capturesphotosapplication.database.ImageDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 22;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    Button btnPicture;
    ImageView imageView;
    private RecyclerView rcvImage;
    private ImageAdapter imageAdapter;
    private List<ImageEntity> mListImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPicture = findViewById(R.id.btncamera);
        imageView = findViewById(R.id.imgview);
        rcvImage = findViewById((R.id.rcv_image));
        mListImage = new ArrayList<>();
        mListImage = ImageDatabase.getInstance(this).imageDAO().getListImages();
        imageAdapter = new ImageAdapter();
        imageAdapter.setData(mListImage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvImage.setLayoutManager(linearLayoutManager);
        rcvImage.setAdapter(imageAdapter);

        imageAdapter.setOnImageClickListener(this::showImagePopup);

        btnPicture.setOnClickListener(view -> {
            if (checkCameraPermission()) {
                openCamera();
            } else {
                requestCameraPermission();
            }
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permission denied. Cannot access camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            saveImageToRoomDatabase(photo);
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void saveImageToRoomDatabase(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageData(byteArray);

        AsyncTask.execute(() -> {
            ImageDatabase.getInstance(this).imageDAO().insertImage(imageEntity);
        });

        Toast.makeText(this, "Image saved success!", Toast.LENGTH_SHORT).show();

        mListImage = ImageDatabase.getInstance(this).imageDAO().getListImages();
        imageAdapter.setData(mListImage);
    }

    private void showImagePopup(Bitmap imageBitmap) {
        ImagePopupDialog imagePopupDialog = new ImagePopupDialog(this);
        imagePopupDialog.setImage(imageBitmap);
        imagePopupDialog.show();
    }
}
