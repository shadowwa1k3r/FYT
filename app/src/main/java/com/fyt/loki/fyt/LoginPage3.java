package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginPage3 extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page3, container, false);
    }


}
