package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Maps_Activity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private FrameLayout bottomSheetLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        coordinatorLayout = findViewById(R.id.coordinate_layout);
        bottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Handle state changes here
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Handle slide offset changes here
            }
        });


        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setFitToContents(true);



    }
}