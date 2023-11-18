package com.vuphan.listform;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> mListUser;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<User> list){
        this.mListUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if (user == null){
            return;
        }

        holder.tvUserName.setText(user.getUsername());
        holder.tvBirthDay.setText(user.getBirthday());
        holder.tvEmail.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return mListUser != null ? mListUser.size() : 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUserName, tvBirthDay, tvEmail;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tv_username);
            tvBirthDay = itemView.findViewById(R.id.tv_birthday);
            tvEmail = itemView.findViewById(R.id.tv_email);
        }
    }
}
