package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class LoginPage2 extends AnimListener {

    private ImageView lp2backimg,mainbg;
    Toolbar tb;
    private Button fb,vk,tw,gp,login;


    public LoginPage2() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginPage2 newInstance() {
        LoginPage2 fragment = new LoginPage2();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onAnimationStarted()
    {
        lp2backimg.setImageDrawable(null);
    }

    @Override
    public void onAnimationEnded()
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        mainbg = (ImageView)getActivity().findViewById(R.id.loginBack);
        Glide.with(getContext()).load(R.drawable.loginpage2).asBitmap().centerCrop().into(mainbg);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View loginPage2 = inflater.inflate(R.layout.fragment_login_page2, container, false);
        lp2backimg = (ImageView)loginPage2.findViewById(R.id.lp2backimg);
        login = (Button)loginPage2.findViewById(R.id.logInto);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setVisibility(View.GONE);

        //Glide.with(getContext()).load(R.drawable.loginpage2).asBitmap().centerCrop().into(lp2backimg);

        tb =(Toolbar)getActivity().findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               lp2backimg.setImageDrawable(null);


                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new LoginPage3()).addToBackStack(null).commit();
            }
        });



        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        return loginPage2;
    }

}
