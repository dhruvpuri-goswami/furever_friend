package com.example.fur_ever_friend;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class AppoinmentAdapter extends RecyclerView.Adapter<AppoinmentAdapter.ViewHolder>{

    private List<AppoinmentModel> appointments;
    private List<PickUpModel> locations;
    private List<String> keys;
    private int check;

    public AppoinmentAdapter(List<AppoinmentModel> appointments,List<PickUpModel> locations,List<String> keys,int check) {
        this.appointments = appointments;
        this.locations=locations;
        this.keys=keys;
        this.check=check;
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
        int pos=position;
        holder.userId.setText(appoinment.getUserId());
        holder.dateForAppoinment.setText(appoinment.getDate());
        holder.timeForAppoinment.setText(appoinment.getTime());
        holder.startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==1) {
                    Intent intent = new Intent(view.getContext(), TrackerActivity.class);
                    intent.putExtra("key", keys.get(pos));
                    view.getContext().startActivity(intent);
                }else{
                    Intent intent=new Intent(view.getContext(),Tracker.class);
                    intent.putExtra("key",keys.get(pos));
                    view.getContext().startActivity(intent);
                }
            }
        });
        PickUpModel pickUpModel=locations.get(position);
        holder.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.getUiSettings().setZoomControlsEnabled(false);
                double lat1=pickUpModel.getLatitude();
                double long1=pickUpModel.getLongtitude();
                LatLng latLng=new LatLng(lat1,long1);
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Pickup Location"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView dateForAppoinment,timeForAppoinment,userId;
        Button startService;
        MapView mapView;

        public ViewHolder(View itemView) {
            super(itemView);
            userId=itemView.findViewById(R.id.userId_tv_value);
            dateForAppoinment=itemView.findViewById(R.id.date_for_appoinment);
            timeForAppoinment = itemView.findViewById(R.id.time_for_appoinment);
            startService=itemView.findViewById(R.id.start_tracking);
            mapView=itemView.findViewById(R.id.pickup_map);
            if(mapView!=null){
                mapView.onCreate(null);
                mapView.onResume();
            }
        }
    }
}
