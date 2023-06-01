package com.example.fur_ever_friend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.MapView;

import java.util.List;

public class Recent_Walker_Adapter extends RecyclerView.Adapter<Recent_Walker_Adapter.ViewHolder> {
    private List<RecentWalkerModel> recent_appointments;

    public Recent_Walker_Adapter(List<RecentWalkerModel> recent_appointments) {
        this.recent_appointments = recent_appointments;
    }

    @NonNull
    @Override
    public Recent_Walker_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_walkers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recent_Walker_Adapter.ViewHolder holder, int position) {
        RecentWalkerModel recentWalkerModel = recent_appointments.get(position);

        holder.dateForAppoinment.setText(recentWalkerModel.getDate());
        holder.timeForAppoinment.setText(recentWalkerModel.getTime());
    }

    @Override
    public int getItemCount() {
        return recent_appointments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateForAppoinment, timeForAppoinment;

        public ViewHolder(View itemView) {
            super(itemView);
            dateForAppoinment = itemView.findViewById(R.id.dog_walker_date);
            timeForAppoinment = itemView.findViewById(R.id.dog_walker_time);
        }
    }
}

