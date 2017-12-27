package com.fyt.loki.fyt.MainAppPage.Menu.Friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyt.loki.fyt.MainAppPage.News.FriendItemAdapterPanel;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendsListOnline extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FriendItemAdapterPanel mAdapterPanel;
    private ArrayList<FriendItemType> mDataset;
    private String BASE_URL,BASE_URL_API;
    private ProfileInterface mProfileInterface;


    public FriendsListOnline() {
        // Required empty public constructor
    }


    public static FriendsListOnline newInstance(String token, String username) {
        FriendsListOnline fragment = new FriendsListOnline();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View FListOnline=inflater.inflate(R.layout.fragment_friends_list_online, container, false);


        BASE_URL = getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mProfileInterface = retrofit.create(ProfileInterface.class);

        mRecyclerView = (RecyclerView)FListOnline.findViewById(R.id.friendsrv_online);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataset = new ArrayList<FriendItemType>();


        Call<List<FriendInfoModel>> mFriendInfo = mProfileInterface.friendInfo(" Token " +mParam1);
        mFriendInfo.enqueue(new Callback<List<FriendInfoModel>>() {
            @Override
            public void onResponse(Call<List<FriendInfoModel>> call, Response<List<FriendInfoModel>> response) {
                if(response.isSuccessful()){
                    mDataset.clear();
                    //  mDataset_panel.clear();


                    for (int i=0; i<response.body().size();i++){

                        if(response.body().get(i).profile.is_online) {
                            mDataset.add(new FriendItemType(response.body().get(i).profile.avatar, response.body().get(i).username, response.body().get(i).profile.is_online,response.body().get(i).id));

                        }



                    }
                    //friends.setText("Friends("+response.body().size()+")");
                    //frcount.setText(String.valueOf(response.body().size()));
                    mAdapterPanel= new FriendItemAdapterPanel(inflater.getContext(),mDataset,mParam1);

                    mRecyclerView.setAdapter(mAdapterPanel);
                }
            }

            @Override
            public void onFailure(Call<List<FriendInfoModel>> call, Throwable t) {

            }
        });

        return FListOnline;
    }


}
