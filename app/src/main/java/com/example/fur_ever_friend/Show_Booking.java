package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show_Booking extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<AppoinmentModel> appoinmentModelList;
    private List<PickUpModel> locations;
    private List<String> keys;
    private AppoinmentAdapter appoinmentAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_booking);
        recyclerView = findViewById(R.id.booking_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("booking");
        appoinmentModelList = new ArrayList<>();
        locations=new ArrayList<>();
        keys=new ArrayList<>();
        appoinmentAdapter = new AppoinmentAdapter(appoinmentModelList,locations,keys,0);
        recyclerView.setAdapter(appoinmentAdapter);

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        String mobile=sharedPreferences.getString("mobile","");


        databaseReference.orderByChild("userId").equalTo(mobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appoinmentModelList.clear();
                locations.clear();
                keys.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    AppoinmentModel appoinmentModel = snapshot1.getValue(AppoinmentModel.class);
                    appoinmentModelList.add(appoinmentModel);
                    PickUpModel pickUpModel=snapshot1.child("Pickup Location").getValue(PickUpModel.class);
                    locations.add(pickUpModel);
                    keys.add(snapshot1.getKey());
                }

                appoinmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}