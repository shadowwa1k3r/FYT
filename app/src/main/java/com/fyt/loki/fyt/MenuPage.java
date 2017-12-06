package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.readystatesoftware.viewbadger.BadgeView;

public class MenuPage extends Fragment {

    private static String mParam1,ARG_PARAM1="param1";
    private static String mParam2,ARG_PARAM2="param2";
    public MenuPage() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MenuPage newInstance(String token,String user_name) {
        MenuPage fragment = new MenuPage();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,token);
        args.putString(ARG_PARAM2,user_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View Menu = inflater.inflate(R.layout.fragment_menu_page, container, false);
        Button my_profile = (Button)Menu.findViewById(R.id.myprofile_btn);
        BadgeView badge = new BadgeView(getContext(),my_profile);
        badge.setText("*");
        badge.show();
        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,new MyProfileSettings()).addToBackStack(null).commit();
            }
        });


        // Inflate the layout for this fragment
        return Menu;
    }

}
