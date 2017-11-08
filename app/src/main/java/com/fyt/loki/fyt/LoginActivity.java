package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {


    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb)
        ;


        getSupportFragmentManager().beginTransaction().replace(R.id.loginPageContainer,new LoginPage1()).commit();

        getSupportActionBar().hide();


    }
}
