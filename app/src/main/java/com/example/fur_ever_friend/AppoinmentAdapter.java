package com.example.fur_ever_friend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppoinmentAdapter extends RecyclerView.Adapter<AppoinmentAdapter.ViewHolder>{

    private List<AppoinmentModel> appointments;
    public AppoinmentAdapter(List<AppoinmentModel> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appoinent_cardview, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AppoinmentAdapter.ViewHolder holder, int position) {
        AppoinmentModel appoinment=appointments.get(position);
        holder.dateForAppoinment.setText(appoinment.getDate());
        holder.timeForAppoinment.setText(appoinment.getTime());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView dateForAppoinment,timeForAppoinment;

        public ViewHolder(View itemView) {
            super(itemView);
            dateForAppoinment=itemView.findViewById(R.id.date_for_appoinment);
            timeForAppoinment = itemView.findViewById(R.id.time_for_appoinment);
        }
    }
}
