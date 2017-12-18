package com.fyt.loki.fyt.MainAppPage.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fyt.loki.fyt.Auth.LoginActivity;
import com.fyt.loki.fyt.MainAppPage.Menu.Settings.MyProfileSettings;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.SharedPreference;
import com.readystatesoftware.viewbadger.BadgeView;

public class MenuPage extends Fragment {

    private static String mParam1,ARG_PARAM1="param1";
    private static String mParam2,ARG_PARAM2="param2";
    private SharedPreference mSharedPreference;
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
        mSharedPreference=new SharedPreference();
        Button my_profile = (Button)Menu.findViewById(R.id.myprofile_btn);
        Button logout=(Button)Menu.findViewById(R.id.logoout);
        BadgeView badge = new BadgeView(getContext(),my_profile);
        badge.setText("*");
        badge.setBadgeMargin(0,0);
        badge.show();
        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,new MyProfileSettings()).addToBackStack(null).commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Error")
                        .setMessage("Are you sure want to logout?")
                        .setIcon(R.drawable.warning)
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSharedPreference.clearSharedPreferences(getContext());
                                Intent intent = new Intent();


                                try {
                                    intent.setClass(getContext(), LoginActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        // Inflate the layout for this fragment
        return Menu;
    }

}
