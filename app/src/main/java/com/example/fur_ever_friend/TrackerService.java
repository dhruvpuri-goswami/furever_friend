package com.example.fur_ever_friend;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TrackerService extends Service {
    NotificationManager manager;
    double lat=0.0, lng=0.0;
    Notification notification;
    LocationManager locationManager;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    String bid;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bid=intent.getStringExtra("bookingid");
        //Log.d("bid",bid);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)||locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildNotification();
            requestLocationUpdates();
        }else{
            Toast.makeText(this, "Enable Location", Toast.LENGTH_SHORT).show();
        }
    }
    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the persistent notification

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyChannelId","name", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);


            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,"MyChannelId");
            notification = notificationBuilder
                    .setSmallIcon(R.drawable.furever)
                    .setContentTitle("Furever-Friend")
                    .setChannelId("MyChannelId")
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setContentIntent(broadcastIntent)
                    .setContentText("Tracking is on. Tap to cancel tracking").build();
        }
        else{
            notification=new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.furever)
                    .setContentTitle("Furever-Friend")
                    .setContentText("Tracking is on. Tap to cancel tracking")
                    .setContentIntent(broadcastIntent)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .build();
        }
//                .setContentTitle("Vehicle Management System")
//                .setContentText("Tracing is On. Tap to cancel tracking.")
//                .setOngoing(true)
//                .setContentIntent(broadcastIntent)
//                .setSmallIcon(R.drawable.logo);
        manager.notify(1,notification);
    }
    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("stop", "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    };
    private void requestLocationUpdates() {
        // Functionality coming next step

        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("tracking");
                final Location location = locationResult.getLastLocation();
                Toast.makeText(getApplicationContext(), "The Coordinates has been sent", Toast.LENGTH_SHORT).show();

                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    //Log.d("bid", bid);
                    Calendar calendar=Calendar.getInstance();
                    String time=String.valueOf(calendar.getTimeInMillis());
                    ref.child(bid).child(time).setValue(location);
                }
            }
        };
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            fusedLocationProviderClient.requestLocationUpdates(request,locationCallback , null);
        }
    }
}
