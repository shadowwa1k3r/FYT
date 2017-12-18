package com.fyt.loki.fyt.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fyt.loki.fyt.MainAppPage.MainPage;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.SharedPreference;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class LoginActivity extends AppCompatActivity {


    String[] descriptionData = {"", "", "", "",""};

    private SharedPreference mSharedPreference;
    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreference = new SharedPreference();
        if(mSharedPreference.getUserName(this)!=null&&mSharedPreference.getToken(this)!=null)
        {
            Intent intent = new Intent();
            intent.setClass(this, MainPage.class);

            Bundle bundle = new Bundle();
            bundle.putString("Token",mSharedPreference.getToken(this));
            bundle.putString("UName",mSharedPreference.getUserName(this));
            intent.putExtras(bundle);


            try {
                startActivity(intent);
                finish();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            setContentView(R.layout.activity_login);




            tb = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(tb)
            ;


            StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.stateProgressBar);
            stateProgressBar.setStateDescriptionData(descriptionData);


            stateProgressBar.setVisibility(View.GONE);
            getSupportActionBar().hide();

            getSupportFragmentManager().beginTransaction().replace(R.id.loginPageContainer, new LoginPage1()).commit();

        }


    }
}
