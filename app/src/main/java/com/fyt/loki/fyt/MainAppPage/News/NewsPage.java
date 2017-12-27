package com.fyt.loki.fyt.MainAppPage.News;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.MainAppPage.News.Feed.NewsFeedItemType;
import com.fyt.loki.fyt.MainAppPage.News.Feed.NewsFeedModel;
import com.fyt.loki.fyt.MainAppPage.News.Feed.NewsPostsAdapter;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.ProfileModel;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.ProfilePage;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ProfileInterface;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsPage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /*-------------------------publish--------------------------------------------
    List<MultipartBody.Part> parts=new ArrayList<MultipartBody.Part>();
    File file;
    RequestBody reqFile;
    RequestBody name,context;
    ViewGroup.LayoutParams mLayoutParams;
    LinearLayout lay;



    private FilePicker mFilePicker;
    private FilePickerCallback mFilePickerCallback;
    //----------------------------------------------------------------------------*/

    private ProfileInterface profileInterface;
    private String mToken;
    private String mUserName;
    private String BASE_URL;
    private String BASE_URL_API;
    private ImageButton sendpost;
    private ImageView add;
    private EditText posttext;
    private Button include;
    private ExpandableLayout media,newstop;
    private TextView new_post;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsPostsAdapter mNewsPostsAdapter;

    private ArrayList<NewsFeedItemType> mDataset;



    static int y;

    private TextView searchtext;
    private SearchView searchview;
    private CircleImageView searchava;
    private Bundle mBundle;



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
        BASE_URL= getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

    }


    @Override
    public void onResume(){
        super.onResume();
        try {

            mNewsPostsAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View NewsPage=inflater.inflate(R.layout.fragment_news_page, container, false);

        FrameLayout fl=(FrameLayout)getActivity().findViewById(R.id.mainFrame);
        fl.setVisibility(View.VISIBLE);
        searchview=(SearchView)NewsPage.findViewById(R.id.search);

        searchtext=(TextView)searchview.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchtext.setTextColor(Color.BLACK);
        searchtext.setHintTextColor(Color.GRAY);
        searchtext.setTextSize(14);
        searchava=(CircleImageView)NewsPage.findViewById(R.id.searchava);



        new_post=(TextView)NewsPage.findViewById(R.id.posttxt);



        mRecyclerView = (RecyclerView)NewsPage.findViewById(R.id.newsRV);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);


        mDataset = new ArrayList<NewsFeedItemType>();

        Call<ProfileModel> mprofileInfo = profileInterface.profileInfo(" Token "+mToken,mUserName);
        mprofileInfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                final String ava = response.body().getProfile().getAvatar();
                try {
                    Glide.with(getActivity()).load(ava).asBitmap().into(searchava);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                final String usnm = response.body().getUsername();
                final Call<List<NewsFeedModel>> getnews = profileInterface.getNews(" Token " + mToken);
                getnews.enqueue(new Callback<List<NewsFeedModel>>() {
                    @Override
                    public void onResponse(Call<List<NewsFeedModel>> call, Response<List<NewsFeedModel>> response) {
                        if (response.isSuccessful()) {
                            mDataset.clear();



                            for (int i = 0; i < response.body().size(); i++) {

                                mDataset.add(new NewsFeedItemType(BASE_URL+response.body().get(i).target.owner.profile.avatar, response.body().get(i).target.owner.username, response.body().get(i).target.created, response.body().get(i).target.context,
                                        response.body().get(i).target.likes_count, response.body().get(i).target.comments, response.body().get(i).target.images, response.body().get(i).target.videos, response.body().get(i).target_id, response.body().get(i).target.likes));

                            }

                            mNewsPostsAdapter = new NewsPostsAdapter(getActivity(), mDataset, mToken, mUserName);

                            mNewsPostsAdapter.notifyDataSetChanged();

                            mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
                            mRecyclerView.setAdapter(mNewsPostsAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewsFeedModel>> call, Throwable t) {
                        Log.e("fail", "onFailure: "+t.toString() );

                    }
                });
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.e("fail", "onFailure: "+t.toString() );

            }
        });



        searchava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.contentContainer, ProfilePage.newInstance(mToken,mUserName)).addToBackStack(null).commit();
            }
        });

        new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.contentContainer,new NewPostPublish()).addToBackStack(null).commit();

            }
        });



        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},10);






        return NewsPage;
    }




}
