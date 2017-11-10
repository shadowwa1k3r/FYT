package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kofigyan.stateprogressbar.StateProgressBar;


public class CreateAccountPage1 extends Fragment {


    public CreateAccountPage1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccountPage1.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAccountPage1 newInstance(String param1, String param2) {
        CreateAccountPage1 fragment = new CreateAccountPage1();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cap1=inflater.inflate(R.layout.fragment_create_account_page1, container, false);
        Button cap2=(Button)cap1.findViewById(R.id.signupphone);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        cap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new CreateAccountPage2()).addToBackStack(null).commit();
            }
        });
        // Inflate the layout for this fragment
        return cap1;
    }

}

