package com.example.fur_ever_friend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Otp_Validation_Activity extends AppCompatActivity {

    Button btn_otp;
    EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    Intent intent;
    String verficationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_validation);
        intent=getIntent();
        TextView mobileContainer=findViewById(R.id.phone_num_container);
        String number=intent.getStringExtra("number");
        verficationId=intent.getStringExtra("verificationId");
        mobileContainer.setText(String.format("+91-%s",number));
        inputCode1=findViewById(R.id.otp_block_1);
        inputCode2=findViewById(R.id.otp_block_2);
        inputCode3=findViewById(R.id.otp_block_3);
        inputCode4=findViewById(R.id.otp_block_4);
        inputCode5=findViewById(R.id.otp_block5);
        inputCode6=findViewById(R.id.otp_block6);
        btn_otp=findViewById(R.id.otp_submit);
        ProgressBar progressBar=findViewById(R.id.otpProgress);
        setupOTP();


        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputCode1.getText().toString().trim().isEmpty()||inputCode2.getText().toString().trim().isEmpty()||inputCode3.getText().toString().trim().isEmpty()||inputCode4.getText().toString().trim().isEmpty()||inputCode5.getText().toString().trim().isEmpty()||inputCode6.getText().toString().trim().isEmpty()){
                    Toast.makeText(Otp_Validation_Activity.this, "Please Enter Valid Otp", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code=inputCode1.getText().toString()+inputCode2.getText().toString()+inputCode3.getText().toString()+inputCode4.getText().toString()+inputCode5.getText().toString()+inputCode6.getText().toString();
                if(verficationId!=null){
                    progressBar.setVisibility(View.VISIBLE);
                    btn_otp.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(verficationId,code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            btn_otp.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()){
                                Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                            }else {
                                Toast.makeText(Otp_Validation_Activity.this, "Code Was Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void setupOTP(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}