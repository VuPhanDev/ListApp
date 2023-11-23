package com.example.capturesphotosapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends  RecyclerView.Adapter<ImageAdapter.ImageViewHodel>{
    private List<ImageEntity> mListImage;
    private OnImageClickListener mOnImageClickListener;
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ImageEntity> list){
        this.mListImage = list;
        notifyDataSetChanged();
    }
    public interface OnImageClickListener {
        void onImageClick(Bitmap imageBitmap);
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.mOnImageClickListener = listener;
    }

    @NonNull
    @Override
    public ImageViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHodel holder, int position) {
        ImageEntity image = mListImage.get(position);
        if (image == null){
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(image.getImageData(), 0, image.getImageData().length);
        holder.imageView.setImageBitmap(bitmap);
        holder.imageView.setOnClickListener(v -> {
            if (mOnImageClickListener != null) {
                mOnImageClickListener.onImageClick(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListImage != null ? mListImage.size() : 0;
    }

    public static class ImageViewHodel extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageViewHodel(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgview);
        }
    }
}
