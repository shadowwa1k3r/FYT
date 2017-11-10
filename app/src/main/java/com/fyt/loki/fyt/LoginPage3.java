package com.fyt.loki.fyt;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

public class LoginPage3 extends AnimListener {


    private Button createAcc;

    ImageView mainbg;
    StateProgressBar stateProgressBar;

    public LoginPage3() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginPage3 newInstance(String param1, String param2) {
        LoginPage3 fragment = new LoginPage3();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onAnimationEnded(){
        stateProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View loginPage3 = inflater.inflate(R.layout.fragment_login_page3, container, false);
        TextView tv6 = (TextView)loginPage3.findViewById(R.id.textView6);
        tv6.setMovementMethod(LinkMovementMethod.getInstance());

        stateProgressBar = (StateProgressBar) getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);





        createAcc = (Button)loginPage3.findViewById(R.id.createAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new CreateAccountPage1()).addToBackStack(null).commit();
            }
        });

        return loginPage3;
    }


}
