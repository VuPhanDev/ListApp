package com.example.capturesphotosapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class ImagePopupDialog extends Dialog {
    private ImageView fullImageView;

    @SuppressLint("MissingInflatedId")
    public ImagePopupDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_image_popup);

        fullImageView = findViewById(R.id.fullImageView);
    }

    public void setImage(Bitmap imageBitmap) {
        fullImageView.setImageBitmap(imageBitmap);
    }
}
