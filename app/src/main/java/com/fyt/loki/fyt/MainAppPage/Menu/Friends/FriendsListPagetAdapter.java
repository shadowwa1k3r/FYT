package com.fyt.loki.fyt.MainAppPage.Menu.Friends;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ergas on 12/11/2017.
 */

public class FriendsListPagetAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;
    String token;
    String username;

    public FriendsListPagetAdapter(FragmentManager fm,int NumOfTabs,String token,String username){
        super(fm);
        this.mNumOfTabs=NumOfTabs;
        this.token=token;
        this.username=username;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                FriendsListAll tab1 = FriendsListAll.newInstance(token,username);
                return tab1;
            case 1:
                FriendsListOnline tab2 = FriendsListOnline.newInstance(token,username);
                return tab2;
            default:
                return null;

        }
    }
    @Override
    public int getCount(){
        return mNumOfTabs;
    }
}
