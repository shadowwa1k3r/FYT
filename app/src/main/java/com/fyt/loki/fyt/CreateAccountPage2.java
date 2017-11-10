package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kofigyan.stateprogressbar.StateProgressBar;


public class CreateAccountPage2 extends Fragment {

    public CreateAccountPage2() {
        // Required empty public constructor
    }

    public static CreateAccountPage2 newInstance() {
        CreateAccountPage2 fragment = new CreateAccountPage2();

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
        View cap2=inflater.inflate(R.layout.fragment_create_account_page2, container, false);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        Button cap3b=(Button)cap2.findViewById(R.id.topage3);
        cap3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new CreateAccountPage3()).addToBackStack(null).commit();

            }
        });

        return cap2;
    }

    /*public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }*/

}
