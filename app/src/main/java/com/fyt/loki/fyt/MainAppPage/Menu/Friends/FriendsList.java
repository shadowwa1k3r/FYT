package com.fyt.loki.fyt.MainAppPage.Menu.Friends;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyt.loki.fyt.R;


public class FriendsList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    FriendsListPagetAdapter mAdapter;



    public FriendsList() {
        // Required empty public constructor
    }

    public static FriendsList newInstance(String token, String username) {
        FriendsList fragment = new FriendsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, token);
        args.putString(ARG_PARAM2, username);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View FList =inflater.inflate(R.layout.fragment_friends_list, container, false);
        mToolbar = (Toolbar)FList.findViewById(R.id.friendslist_tb);
        mTabLayout=(TabLayout)FList.findViewById(R.id.friendslist_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText("All Friends(100)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Online(10)"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        mViewPager=(ViewPager)FList.findViewById(R.id.friendslist_vp);
        mAdapter=new FriendsListPagetAdapter(getChildFragmentManager(),mTabLayout.getTabCount(),mParam1,mParam2);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return FList;
    }

}
