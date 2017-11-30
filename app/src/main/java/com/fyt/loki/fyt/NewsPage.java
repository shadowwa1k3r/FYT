package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsPage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ProfileInterface profileInterface;
    private String mToken;
    private String mUserName;
    private String BASE_URL;
    private  String BASE_URL_API;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsPostsAdapter mNewsPostsAdapter;
    private ArrayList<NewsFeedItemType> mDataset;




    public static NewsPage newInstance(String param1, String param2) {
        NewsPage fragment = new NewsPage();
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
            mToken = getArguments().getString(ARG_PARAM1);
            mUserName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View NewsPage=inflater.inflate(R.layout.fragment_news_page, container, false);
        BASE_URL= getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";
        FrameLayout fl=(FrameLayout)getActivity().findViewById(R.id.mainFrame);
        fl.setVisibility(View.VISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        mRecyclerView = (RecyclerView)NewsPage.findViewById(R.id.newsRV);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataset = new ArrayList<NewsFeedItemType>();

        Call<ProfileModel> mprofileInfo = profileInterface.profileInfo(" Token "+mToken,mUserName);
        mprofileInfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                final String ava = BASE_URL + response.body().getAvatar();
                final String usnm = response.body().getUsername();
                final Call<List<NewsFeedModel>> getnews = profileInterface.getNews(" Token "+mToken);
                getnews.enqueue(new Callback<List<NewsFeedModel>>() {
                    @Override
                    public void onResponse(Call<List<NewsFeedModel>> call, Response<List<NewsFeedModel>> response) {
                        if (response.isSuccessful()){
                            mDataset.clear();


                            //Button button = (Button)NewsPage.findViewById(R.id.button17);

                            for (int i = 0; i <response.body().size(); i++) {

                                mDataset.add(new NewsFeedItemType(response.body().get(i).getTarget().getAvatar(),response.body().get(i).getTarget().getOwner(),response.body().get(i).target.getCreated(),response.body().get(i).target.getContext(),
                                        response.body().get(i).target.getLikes_count(),response.body().get(i).target.getComments(),response.body().get(i).target.getImages(),response.body().get(i).target.getVideos(),response.body().get(i).getTarget_id(),response.body().get(i).target.getLikes()));





                            }




                            mNewsPostsAdapter = new NewsPostsAdapter(getActivity(),mDataset,mToken);

                            mNewsPostsAdapter.notifyDataSetChanged();

                            mRecyclerView.setAdapter(mNewsPostsAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewsFeedModel>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });

        return NewsPage;
    }

}
