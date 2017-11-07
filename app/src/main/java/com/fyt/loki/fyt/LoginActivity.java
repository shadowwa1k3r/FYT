package com.fyt.loki.fyt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LoginActivity extends AppCompatActivity {

    ImageView loginBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBackground = (ImageView)findViewById(R.id.loginBack);
        Glide.with(getApplicationContext()).load(R.drawable.signup_background).asBitmap().centerCrop().into(loginBackground);


    }
}
