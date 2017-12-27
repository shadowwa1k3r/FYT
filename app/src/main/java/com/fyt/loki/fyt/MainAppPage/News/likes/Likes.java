package com.fyt.loki.fyt.MainAppPage.News.likes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendInfoModel;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendItemType;
import com.fyt.loki.fyt.MainAppPage.News.Feed.NewsFeedItemType;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.Tools.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Likes extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private NewsFeedItemType feed;
    private RecyclerView likers;
    private RecyclerView.LayoutManager mLayoutManager;
    private LikersAdapter mLikersAdapter;
    private ArrayList<FriendItemType> likersList;
    private String BASE_URL,BASE_URL_API;
    private ProfileInterface mProfileInterface;
    private SharedPreference mSharedPreference;



    public Likes() {
        // Required empty public constructor
    }


    public static Likes newInstance(NewsFeedItemType feed) {
        Likes fragment = new Likes();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, feed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feed = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Likes=inflater.inflate(R.layout.fragment_likes, container, false);
        likers=(RecyclerView)Likes.findViewById(R.id.likers);
        BASE_URL = getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";
        mSharedPreference=new SharedPreference();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mProfileInterface = retrofit.create(ProfileInterface.class);

        likers.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getActivity());
        likers.setLayoutManager(mLayoutManager);

        likersList=new ArrayList<>();

        Call<List<FriendInfoModel>> friendlist=mProfileInterface.friendInfo(" Token "+ mSharedPreference.getToken(getContext()));
        friendlist.enqueue(new Callback<List<FriendInfoModel>>() {
            @Override
            public void onResponse(Call<List<FriendInfoModel>> call, Response<List<FriendInfoModel>> response) {
                if(response.isSuccessful()){
                    for (int i = 0; i <response.body().size() ; i++) {
                        for (int j = 0; j <feed.getLikes().size() ; j++) {


                            /*if (response.body().get(i).id == feed.getLikes().get(j)) {
                                likersList.add(new FriendItemType(response.body().get(i).profile.avatar, response.body().get(i).username,
                                        response.body().get(i).profile.is_online, response.body().get(i).id));
                            }*/
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<FriendInfoModel>> call, Throwable t) {

            }
        });

        return Likes;
    }

}
