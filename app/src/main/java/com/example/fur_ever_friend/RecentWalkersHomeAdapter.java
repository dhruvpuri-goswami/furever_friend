package com.example.fur_ever_friend;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

public class RecentWalkersHomeAdapter extends RecyclerView.Adapter<RecentWalkersHomeAdapter.ViewHolder> {
    private List<RecentWalkerModel> recent_appointments;
    private Context context;

    public RecentWalkersHomeAdapter(List<RecentWalkerModel> recent_appointments) {
        this.recent_appointments = recent_appointments;

    }

    @NonNull
    @Override
    public RecentWalkersHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_walkers_for_home_screen, parent, false);
        context=parent.getContext();
        return new RecentWalkersHomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecentWalkersHomeAdapter.ViewHolder holder, int position) {
        RecentWalkerModel recentWalkerModel = recent_appointments.get(position);
        holder.dateForAppoinment.setText(recentWalkerModel.getDate());
        holder.timeForAppoinment.setText(recentWalkerModel.getTime());
        holder.walker_name.setText(recentWalkerModel.getName());
        Glide.with(context).load(recentWalkerModel.getImageUrl()).into(holder.walker_img);
    }

    @Override
    public int getItemCount() {
        return recent_appointments.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateForAppoinment, timeForAppoinment,walker_name;
        ImageView walker_img;
        public ViewHolder(View itemView) {
            super(itemView);
            dateForAppoinment = itemView.findViewById(R.id.recent_walker_date);
            timeForAppoinment = itemView.findViewById(R.id.recent_walker_time);
            walker_img=itemView.findViewById(R.id.recent_walker_img);
            walker_name=(TextView) itemView.findViewById(R.id.recent_walker_name);
        }
    }
}
