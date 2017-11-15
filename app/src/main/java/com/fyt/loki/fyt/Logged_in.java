package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Logged_in extends Fragment {


    public Logged_in() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Logged_in newInstance() {
        Logged_in fragment = new Logged_in();

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
        return inflater.inflate(R.layout.fragment_logged_in, container, false);
    }


}
