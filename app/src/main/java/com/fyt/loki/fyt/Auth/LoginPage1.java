package com.fyt.loki.fyt.Auth;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.AnimListener;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class LoginPage1 extends AnimListener {

    private Button logIn;
    ImageView loginBackground;
    public LoginPage1() {
        // Required empty public constructor
    }

    public static LoginPage1 newInstance() {
        LoginPage1 fragment = new LoginPage1();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View loginPage1 = inflater.inflate(R.layout.login_page_1,container,false);


       StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setVisibility(View.GONE);
        loginBackground = (ImageView)getActivity().findViewById(R.id.loginBack);
        Glide.with(getContext()).load(R.drawable.signup_background).asBitmap().centerCrop().into(loginBackground);
        //loginBackground.setImageResource(R.drawable.signup_background);
        loginBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        logIn = (Button)loginPage1.findViewById(R.id.logIn);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)/*.setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright)*/.replace(R.id.loginPageContainer,new LoginPage2()).addToBackStack(null).commit();
            }
        });

        return loginPage1;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}
