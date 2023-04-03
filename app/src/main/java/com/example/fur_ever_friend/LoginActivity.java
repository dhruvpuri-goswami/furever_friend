package com.example.fur_ever_friend;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button register,forget;
    EditText password;
    ImageView showHide;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    Button login_btn;
    EditText email;
    Button phone_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        showHide=findViewById(R.id.showhide);
        phone_btn=findViewById(R.id.phone_btn);
        Button register=findViewById(R.id.register_btn);
        login_btn=findViewById(R.id.login_btn);
        Button forget=findViewById(R.id.forget_pass);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Register.class);
                startActivity(intent);

            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Forgot_Password_Activity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text=email.getText().toString();
                String Password=password.getText().toString();
                if (email_text.isEmpty() ||Password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Fill Blank Details", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(email_text)){
                                String getPassword=snapshot.child(email_text).child("Password").getValue().toString();
                                String role=snapshot.child(email_text).child("Role").getValue().toString();
                                if(Password.equals(getPassword)){
                                    if(role.equals("Walker")){
                                        Toast.makeText(LoginActivity.this, "You are walker", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SendOTP.class);
                startActivity(intent);
            }
        });
    }
    public void ShowHide(View view) {
        if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showHide.setImageResource(R.drawable.lock_item);
        }else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showHide.setImageResource(R.drawable.eye_for_toggle);
        }
    }
}