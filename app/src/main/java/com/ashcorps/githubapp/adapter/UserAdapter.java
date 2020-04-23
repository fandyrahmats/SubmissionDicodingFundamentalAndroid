package com.ashcorps.githubapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashcorps.githubapp.R;
import com.ashcorps.githubapp.ui.UserDetailActivity;
import com.ashcorps.githubapp.model.UserItems;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewModel> {

    ArrayList<UserItems> mUserList;

    public UserAdapter(ArrayList<UserItems> mUserList) {
        this.mUserList = mUserList;
    }

    public void setUserList(ArrayList<UserItems> mUserList) {
        this.mUserList = mUserList;
        notifyDataSetChanged();
    }

    public void clearList() {
        mUserList.clear();
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_user, parent, false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        UserItems item = mUserList.get(position);
        holder.tvName.setText(item.getLogin());
        holder.tvSlogan.setText(String.valueOf(item.getId()));
        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .placeholder(R.drawable.ic_person_placeholder)
                .into(holder.imgProfile);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), UserDetailActivity.class);
            intent.putExtra(UserDetailActivity.KEY_ITEM, item);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_slogan)
        TextView tvSlogan;

        @BindView(R.id.img_profile)
        ImageView imgProfile;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
