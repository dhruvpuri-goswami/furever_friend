package com.example.fur_ever_friend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Dash_BoardFragment extends Fragment {

    private MapView mapView;
    private BottomSheetBehavior bottomSheetBehavior;
    private FrameLayout bottomSheetLayout;

    public Dash_BoardFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_dash__board, container, false);
//        mapView = view.findViewById(R.id.mapView);
        bottomSheetLayout = view.findViewById(R.id.bottom_sheet_layout);

//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                // Set up the map
//                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                googleMap.getUiSettings().setZoomControlsEnabled(true);
//
//                // Add a marker to the map
//                LatLng markerLocation = new LatLng(37.7749, -122.4194);
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(markerLocation);
//                googleMap.addMarker(markerOptions);
//
//                // Move the camera to the marker location
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(markerLocation, 10);
//                googleMap.moveCamera(cameraUpdate);
//            }
//        });
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        return view;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }

}