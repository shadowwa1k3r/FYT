package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CreateAccountPage4 extends Fragment {

    public CreateAccountPage4() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateAccountPage4 newInstance(String param1, String param2) {
        CreateAccountPage4 fragment = new CreateAccountPage4();

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
        return inflater.inflate(R.layout.fragment_create_account_page4, container, false);
    }

}
