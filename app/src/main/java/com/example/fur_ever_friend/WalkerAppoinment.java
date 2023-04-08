package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WalkerAppoinment extends AppCompatActivity {

    private static final String TAG = "WalkerAppoinment";
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<AppoinmentModel> appoinmentModelList;
    private AppoinmentAdapter appoinmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walker_appoinment);

        recyclerView = findViewById(R.id.appoinment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("booking");
        appoinmentModelList = new ArrayList<>();
        appoinmentAdapter = new AppoinmentAdapter(appoinmentModelList);
        recyclerView.setAdapter(appoinmentAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appoinmentModelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AppoinmentModel appoinmentModel = dataSnapshot.getValue(AppoinmentModel.class);
                    appoinmentModelList.add(appoinmentModel);
                }
                appoinmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error retrieving data", error.toException());
            }
        });
    }
}
