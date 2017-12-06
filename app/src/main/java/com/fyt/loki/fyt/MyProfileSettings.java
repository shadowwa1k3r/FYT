package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MyProfileSettings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public MyProfileSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfileSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileSettings newInstance(String param1, String param2) {
        MyProfileSettings fragment = new MyProfileSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ProfSet = inflater.inflate(R.layout.fragment_my_profile_settings, container, false);
        Button general,privacy,notification,blacklist;
        general=(Button)ProfSet.findViewById(R.id.general);
        privacy=(Button)ProfSet.findViewById(R.id.privacy);
        notification=(Button)ProfSet.findViewById(R.id.notification);
        blacklist=(Button)ProfSet.findViewById(R.id.blacklist);

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,new MyProfileSettingsGeneral()).addToBackStack(null).commit();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,new MyProfileSettingsPrivacy()).addToBackStack(null).commit();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,new MyProfileSettingsNotification()).addToBackStack(null).commit();
            }
        });
        blacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,new MyProfileSettingsBlockandComplain()).addToBackStack(null).commit();
            }
        });


        return ProfSet;
    }

}
