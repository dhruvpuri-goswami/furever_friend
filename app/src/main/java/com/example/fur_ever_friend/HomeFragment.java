package com.example.fur_ever_friend;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationKt;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {
    private RecentWalkersHomeAdapter recentWalkersHomeAdapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("booking");
    private DatabaseReference myRefWalker = database.getReference("dog_walkers");
    LocationManager locationManager;
    String provider;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    ImageView profileImage;
    Button book, see_all;
    String name,imageUrl,date,time;

    private int radius = 30;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String mobile = sharedPreferences.getString("mobile", "");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = locationManager.getBestProvider(new Criteria(), false);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);


            databaseReference.child("location").child(mobile).setValue(location);
        }
        book = getActivity().findViewById(R.id.book);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        see_all = view.findViewById(R.id.see_all);
        recyclerView = view.findViewById(R.id.recent_walkers_home);

        List<RecentWalkerModel> recentWalkerModels = new ArrayList<>();
        recentWalkersHomeAdapter = new RecentWalkersHomeAdapter(recentWalkerModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Bundle args = getArguments();
        if (args != null) {
            String walkerId = args.getString("walkerId");
            String userId = args.getString("userId");

            // Use the data as needed
        }
        recyclerView.setAdapter(recentWalkersHomeAdapter);

        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "See all clicked", Toast.LENGTH_SHORT).show();
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recentWalkerModels.clear();
                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    DataSnapshot pickupLocationSnapshot = bookingSnapshot.child("Pickup Location");
                     date = bookingSnapshot.child("date").getValue(String.class);
                     time = bookingSnapshot.child("time").getValue(String.class);

                    String walkerId=bookingSnapshot.child("walkerId").getValue().toString();
                    myRefWalker.child(walkerId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Log.d("image",snapshot.getValue(String.class));

                            imageUrl=snapshot.child("imageUrl").getValue(String.class);
                            name =snapshot.child("name").getValue(String.class);
                            RecentWalkerModel recentWalkerModel=new RecentWalkerModel(date,time, imageUrl,name);

                            recentWalkerModels.add(recentWalkerModel);
                            recentWalkersHomeAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    String name=bookingSnapshot.child("name").getValue(String.class);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });


        return view;
    }
}