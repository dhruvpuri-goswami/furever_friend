package com.example.fur_ever_friend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    Button register,forget;
    EditText password;
    ImageView showHide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password=findViewById(R.id.full_name);
        showHide=findViewById(R.id.showhide);
        Button register=findViewById(R.id.register_btn);
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

    }
    public void ShowHide(View view) {
        if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showHide.setImageResource(R.drawable.lock_item);
        }else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showHide.setImageResource(R.drawable.baseline_remove_red_eye_24);
        }
    }
}