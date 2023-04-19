package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.example.fur_ever_friend.directionhelpers.FetchURL;
import com.example.fur_ever_friend.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tracker extends AppCompatActivity implements loadData, TaskLoadedCallback {
    MapView mapView;
    GoogleMap map;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    List<LatLng> locations;
    Polyline currentPolyline;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker2);
        Intent intent=getIntent();
        String key=intent.getStringExtra("key");
        progressDialog=new ProgressDialog(Tracker.this);
        mapView = findViewById(R.id.mapView3);
        mapView.onCreate(savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("tracking");
        Context context=getApplicationContext();
        //locations=new ArrayList<>();
        locationManager = (LocationManager) Tracker.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enable=false;
        boolean network_enabled=false;
        try{
            gps_enable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {

        }
        try {
            network_enabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){}
        if(!gps_enable&&!network_enabled){
            new AlertDialog.Builder(Tracker.this)
                    .setMessage("Location is not Enabled")
                    .setPositiveButton("Open Location", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .create()
                    .show();
        }else {

            getData(this::onDataLoaded,key);

        }


    }
    private void getData(loadData loadData,String key){
        List<LatLng> data=new ArrayList<>();
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    data.add(new LatLng(Double.parseDouble(snapshot1.child("latitude").getValue().toString()),Double.parseDouble(snapshot1.child("longitude").getValue().toString())));
                }
                loadData.onDataLoaded(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDataLoaded(List<LatLng> data) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map=googleMap;
                ArrayList<LatLng> points=new ArrayList<>();
                PolylineOptions polylineOptions=new PolylineOptions();
//                for(int i=0;i<data.size();i++){
//                    LatLng point=data.get(i);
//                    LatLng position=new LatLng(point.latitude,point.longitude);
//                    points.add(position);
//                }
                String url=getURL(data.get(0),data.get(data.size()-1),"driving");
                new FetchURL(Tracker.this).execute(url,"driving");
                map.addMarker(new MarkerOptions().position(data.get(0)));
                map.addMarker(new MarkerOptions().position(data.get(data.size()-1)));
                polylineOptions.add(data.get(0));
                polylineOptions.add(data.get(data.size()-1));
                polylineOptions.width(12);
            }
        });
    }
    private String getURL(LatLng origin,LatLng dest,String directionMode){
        String str_origin="origin="+origin.latitude+","+origin.longitude;
        String str_dest="destination="+dest.latitude+","+dest.longitude;
        String mode="mode="+directionMode;
        String parameters=str_origin+"&"+str_dest+"&"+mode;
        String output="json";
        String url="https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&key="+getString(R.string.key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline!=null){
            currentPolyline.remove();
        }
        currentPolyline=map.addPolyline((PolylineOptions) values[0]);
    }
}