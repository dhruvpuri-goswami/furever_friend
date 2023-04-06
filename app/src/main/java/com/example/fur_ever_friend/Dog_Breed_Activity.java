package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dog_Breed_Activity extends AppCompatActivity  implements SearchView.OnQueryTextListener{
    RecyclerView recyclerView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("breeds");

    DogBreedAdapter adapter; // declare adapter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_breed);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        List<Dog> dogs = new ArrayList<>(); // create an empty list of dogs


        // Attach a listener to the database reference to read the data
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dogs.clear(); // clear the list of dogs
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dog dog = dataSnapshot.getValue(Dog.class);
                    dogs.add(dog);
                }
                Log.d("Dog_Breed_Activity", "Number of dogs retrieved: " + dogs.size());
                adapter = new DogBreedAdapter(dogs); // pass the empty list to the adapter constructor

                adapter.updateData(dogs); // update the adapter data
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Dog_Breed_Activity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Dog> filteredList = new ArrayList<>();
        for (Dog dog : adapter.getDogsFull()) {
            if (dog.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(dog);
            }
        }
        adapter.setDogs(filteredList);
        return true;

    }
}
