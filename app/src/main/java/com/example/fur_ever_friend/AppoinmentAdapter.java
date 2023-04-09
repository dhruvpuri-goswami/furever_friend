package com.example.fur_ever_friend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public AppoinmentAdapter(List<AppoinmentModel> appointments,List<PickUpModel> locations) {
        this.appointments = appointments;
        this.locations=locations;
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


        TextView dateForAppoinment,timeForAppoinment;
        MapView mapView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateForAppoinment=itemView.findViewById(R.id.date_for_appoinment);
            timeForAppoinment = itemView.findViewById(R.id.time_for_appoinment);
            mapView=itemView.findViewById(R.id.pickup_map);
            if(mapView!=null){
                mapView.onCreate(null);
                mapView.onResume();
            }
        }
    }
}
