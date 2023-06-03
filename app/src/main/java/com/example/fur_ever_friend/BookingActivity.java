package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {


    static EditText editTextDate,editTextTime;
    RecyclerView recyclerView;
    static AppCompatButton booking_btn;
    Button selectLocation;
    DogWalkerAdapter dogWalkerAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("dog_walkers");
    DatabaseReference databaseReference=database.getReference("booking");
    androidx.appcompat.widget.SearchView searchView;
    TextView selectDogwalkerTv,locationDone;
    LatLng latLng;
    static  final int PICK_UP_POINT=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextTime=findViewById(R.id.edit_text_time);
        recyclerView = findViewById(R.id.walkers_recyclerview);
        searchView=findViewById(R.id.search_view_dog_walker);
        selectDogwalkerTv=findViewById(R.id.select_dog_walker_tv);
        booking_btn=findViewById(R.id.book_now);
        selectLocation=findViewById(R.id.select_location);
        searchView.setOnQueryTextListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        locationDone=findViewById(R.id.location_select);
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
                                //dateForBookingtv.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
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
                                //timeForBookingtv.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextDate.getText().toString().isEmpty() && editTextTime.getText().toString().isEmpty())
                {
                    Toast.makeText(BookingActivity.this, "Please select Valid Date and Time", Toast.LENGTH_SHORT).show();
                }
                else if (editTextDate.getText().toString().isEmpty())
                {
                    Toast.makeText(BookingActivity.this, "Please select Valid Date", Toast.LENGTH_SHORT).show();
                } else if (editTextTime.getText().toString().isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Please select Valid Time", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseMessaging.getInstance().getToken()
                            .addOnSuccessListener(new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String token) {
                                    Log.d(Utils.TAG,token);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(BookingActivity.this, "Failed to get token", Toast.LENGTH_SHORT).show();
                                }
                            });
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BookingActivity.this);
                    View layout= LayoutInflater.from(BookingActivity.this).inflate(R.layout.bottom_sheet,null);
                    DogWalker selectedDogWalker=dogWalkerAdapter.getSelectedItem();
                    if(selectedDogWalker!=null&&latLng!=null){
                        bottomSheetDialog.setContentView(layout);
                        bottomSheetDialog.setCancelable(true);
                        bottomSheetDialog.setCanceledOnTouchOutside(true);
                        bottomSheetDialog.show();
                        TextView dogWalkerName=layout.findViewById(R.id.dog_walker_name);
                        ImageView dogWalkerImage=layout.findViewById(R.id.dog_walker_image);
                        Glide.with(getApplicationContext()).load(selectedDogWalker.getImageUrl()).into(dogWalkerImage);
                        dogWalkerName.setText(selectedDogWalker.getName());
                        TextView dogWalkerDate=layout.findViewById(R.id.date_for_booking);
                        dogWalkerDate.setText(editTextDate.getText());
                        TextView dogWalkerTime=layout.findViewById(R.id.time_for_booking);
                        dogWalkerTime.setText(editTextTime.getText());
                        String walkerId=selectedDogWalker.getmobile();
                        Booking booking=new Booking(walkerId,editTextDate.getText().toString(),editTextTime.getText().toString(),getUserId());
                        databaseReference.child(editTextDate.getText().toString().replace("/","")+""+editTextTime.getText().toString().replace(":","")).setValue(booking);
                        databaseReference.child(editTextDate.getText().toString().replace("/","")+""+editTextTime.getText().toString().replace(":","")).child("Pickup Location").child("Latitude").setValue(latLng.latitude);
                        databaseReference.child(editTextDate.getText().toString().replace("/","")+""+editTextTime.getText().toString().replace(":","")).child("Pickup Location").child("Longtitude").setValue(latLng.longitude);
                        Toast.makeText(BookingActivity.this, "Booking success", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(BookingActivity.this, "Fill Empty Details", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickUpPoint=new Intent(BookingActivity.this,Maps_Activity.class);
                startActivityForResult(pickUpPoint,PICK_UP_POINT);
            }
        });
        List<DogWalker> dogWalkers = new ArrayList<>();
        myRef.orderByChild("status").equalTo("Available").addValueEventListener(new ValueEventListener() {
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
        if (selectDogwalkerTv.getVisibility()==View.GONE){
            selectDogwalkerTv.setVisibility(View.VISIBLE);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (selectDogwalkerTv.getVisibility()==View.VISIBLE){
            selectDogwalkerTv.setVisibility(View.GONE);
        }
        List<DogWalker> filteredList = new ArrayList<>();
        for (DogWalker dogWalker : dogWalkerAdapter.getDogsWalkersFull()) {
            if (dogWalker.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(dogWalker);
            }
        }
        dogWalkerAdapter.setDogWalkers(filteredList);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_UP_POINT){
            if(data!=null) {
                latLng = data.getParcelableExtra("picked_point");
                selectLocation.setVisibility(View.GONE);
                locationDone.setVisibility(View.VISIBLE);
            }else{
                new AlertDialog.Builder(BookingActivity.this)
                        .setTitle("Select Location")
                        .setMessage("Pickup Location is not Selected")
                        .setPositiveButton("Select Location", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent pickUpPoint=new Intent(BookingActivity.this,Maps_Activity.class);
                                startActivityForResult(pickUpPoint,PICK_UP_POINT);
                            }
                        }).create()
                        .show();
            }

        }
    }
    public String getUserId(){
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        return sharedPreferences.getString("mobile","");
    }
}