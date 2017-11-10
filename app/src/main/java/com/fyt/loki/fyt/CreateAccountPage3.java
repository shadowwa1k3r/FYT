package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kofigyan.stateprogressbar.StateProgressBar;


public class CreateAccountPage3 extends Fragment {


    public CreateAccountPage3() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateAccountPage3 newInstance(String param1, String param2) {
        CreateAccountPage3 fragment = new CreateAccountPage3();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cap3=inflater.inflate(R.layout.fragment_create_account_page3, container, false);

        Button tcap4 = (Button)cap3.findViewById(R.id.tocap4);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);


        tcap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new CreateAccountPage4()).addToBackStack(null).commit();
            }
        });

        return cap3;
    }

}
