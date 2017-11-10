package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kofigyan.stateprogressbar.StateProgressBar;

public class LoginActivity extends AppCompatActivity {


    String[] descriptionData = {"", "", "", "",""};

    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb)
        ;


        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.stateProgressBar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        stateProgressBar.setVisibility(View.GONE);
        getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.loginPageContainer,new LoginPage1()).commit();




    }
}
