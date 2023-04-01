package com.example.fur_ever_friend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Otp_Validation_Activity extends AppCompatActivity {

    Button btn_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_validation);

        btn_otp=findViewById(R.id.otp_submit);
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Otp_Validation_Activity.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}