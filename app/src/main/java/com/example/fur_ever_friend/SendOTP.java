package com.example.fur_ever_friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        EditText inputMobile=findViewById(R.id.mobile);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading);
        Button submit=findViewById(R.id.buttonOfOTP);
        ImageView back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(SendOTP.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }else {
                    submit.setVisibility(View.INVISIBLE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    inputMobile.setEnabled(false);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+inputMobile.getText().toString(),
                            60, TimeUnit.SECONDS,
                            SendOTP.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            submit.setVisibility(View.VISIBLE);
                            lottieAnimationView.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            submit.setVisibility(View.VISIBLE);
                            inputMobile.setEnabled(true);
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                            Log.d("error",e.getMessage());
                            Toast.makeText(SendOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);
                            submit.setVisibility(View.VISIBLE);
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(getApplicationContext(), Otp_Validation_Activity.class);
                            intent.putExtra("number", inputMobile.getText().toString());
                            intent.putExtra("verificationId",s);
                            startActivity(intent);
                        }
                    });

                }
            }
        });
    }
}