package com.example.fur_ever_friend;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NewFragment extends Fragment {

    private Recent_Walker_Adapter recent_walker_adapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("booking");
    private DatabaseReference myRefWalker = database.getReference("dog_walkers");
    String name,imageUrl,date,time;
    public NewFragment() {
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
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        recyclerView = view.findViewById(R.id.recent_walkers);


        List<RecentWalkerModel> recentWalkerModels = new ArrayList<>();
        recent_walker_adapter = new Recent_Walker_Adapter(recentWalkerModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recent_walker_adapter);

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
                            recent_walker_adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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
