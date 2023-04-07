package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    EditText editTextDate,editTextTime;
    RecyclerView recyclerView;

    DogWalkerAdapter dogWalkerAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("breeds");
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextTime=findViewById(R.id.edit_text_time);
        recyclerView = findViewById(R.id.walkers_recyclerview);
        searchView=findViewById(R.id.search_view_dog_walker);
        searchView.setOnQueryTextListener(this);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // set the selected date to the EditText
                                editTextDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editTextTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        List<DogWalker> dogWalkers = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dogWalkers.clear(); // clear the list of dogs
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DogWalker dogWalker = dataSnapshot.getValue(DogWalker.class);
                    dogWalkers.add(dogWalker);
                }

                dogWalkerAdapter = new DogWalkerAdapter(dogWalkers);

                dogWalkerAdapter.updateData(dogWalkers);
                recyclerView.setAdapter(dogWalkerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookingActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<DogWalker> filteredList = new ArrayList<>();
        for (DogWalker dogWalker : dogWalkerAdapter.getDogsWalkersFull()) {
            if (dogWalker.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(dogWalker);
            }
        }
        dogWalkerAdapter.setDogWalkers(filteredList);
        return true;
    }
}