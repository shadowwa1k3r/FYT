package com.fyt.loki.fyt.MainAppPage.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyt.loki.fyt.R;


public class MapPage extends Fragment {

    public MapPage() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MapPage newInstance() {
        MapPage fragment = new MapPage();

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
        return inflater.inflate(R.layout.fragment_map_page, container, false);
    }


}
