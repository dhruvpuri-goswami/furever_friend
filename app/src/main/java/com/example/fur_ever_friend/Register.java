package com.example.fur_ever_friend;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText email,fullname,mobile,password;
    RadioGroup radioGroup;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
          fullname=findViewById(R.id.full_name);
          email=findViewById(R.id.email);
          mobile=findViewById(R.id.phone_num);
         password=findViewById(R.id.password);
         radioGroup=findViewById(R.id.roleGroup);
         sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
         editor=sharedPreferences.edit();
         Button registerBtn=findViewById(R.id.continue_btn);
        Toast.makeText(getApplicationContext(), "Please scroll down to view the full screen", Toast.LENGTH_SHORT).show();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text=email.getText().toString();
                String fullName=fullname.getText().toString();
                String mobile_num=mobile.getText().toString();
                String Password=password.getText().toString();
                final String role;
                int selectId=radioGroup.getCheckedRadioButtonId();

                if(fullName.isEmpty()||email_text.isEmpty()||mobile_num.isEmpty()||Password.isEmpty()||selectId==-1){
                    Toast.makeText(Register.this, "Please Fill All Blank Details", Toast.LENGTH_SHORT).show();
                }else{
                    RadioButton roleButton=radioGroup.findViewById(selectId);
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobile_num)){
                                Toast.makeText(Register.this, "Phone is already Registred", Toast.LENGTH_SHORT).show();
                            }else{
                                editor.putString("mobile",mobile_num);
                                editor.putString("role",roleButton.getText().toString());
                                databaseReference.child("users").child(mobile_num).child("Fullname").setValue(fullName);
                                databaseReference.child("users").child(mobile_num).child("Email").setValue(email_text);
                                databaseReference.child("users").child(mobile_num).child("Password").setValue(Password);
                                databaseReference.child("users").child(mobile_num).child("Role").setValue(roleButton.getText());
                                if(roleButton.getText().toString().equals("Walker")){
                                    DogWalker dogWalker=new DogWalker(email_text,"",mobile_num,fullName,"","Available");
                                    databaseReference.child("dog_walkers").child(mobile_num).setValue(dogWalker);
//                                    databaseReference.child("dog_walkers").child(mobile_num).child("Fullname").setValue(fullName);
//                                    databaseReference.child("dog_walkers").child(mobile_num).child("Mobile").setValue(email_text);
//                                    databaseReference.child("dog_walkers").child(mobile_num).child("Status").setValue("Available");
                                }
                                Toast.makeText(Register.this, "Users Registred", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }
}