package com.fyt.loki.fyt.MainAppPage.Menu.Friends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.fyt.loki.fyt.MainAppPage.News.FriendItemAdapterPanel;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.ProfileModel;
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


public class SearchUser extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button searchuser;
    private EditText byname;
    private RecyclerView result;

    private RecyclerView.LayoutManager mLayoutManager;
    private FriendItemAdapterPanel resultadapter;
    private ArrayList<FriendItemType> resultlist;
    private String BASE_URL,BASE_URL_API;
    private ProfileInterface mProfileInterface;
    private SharedPreference mSharedPreference;
    public SearchUser() {
        // Required empty public constructor
    }


    public static SearchUser newInstance(String param1, String param2) {
        SearchUser fragment = new SearchUser();
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

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View Search=inflater.inflate(R.layout.fragment_search_user, container, false);
        searchuser=(Button)Search.findViewById(R.id.search_user);
        byname=(EditText) Search.findViewById(R.id.byname);
        result=(RecyclerView)Search.findViewById(R.id.searchresult);

        BASE_URL = getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";
        mSharedPreference=new SharedPreference();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mProfileInterface = retrofit.create(ProfileInterface.class);

        result.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getActivity());
        result.setLayoutManager(mLayoutManager);

        resultlist=new ArrayList<>();

        searchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<ProfileModel>> search_user = mProfileInterface.search_user(" Token "+mSharedPreference.getToken(getContext()),byname.getText().toString());
                search_user.enqueue(new Callback<List<ProfileModel>>() {
                    @Override
                    public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                        if(response.isSuccessful()){
                            resultlist.clear();
                            for (int i = 0; i <response.body().size() ; i++) {
                                resultlist.add(new FriendItemType(response.body().get(i).getProfile().getAvatar(),response.body().get(i).getUsername(),response.body().get(i).getProfile().getIsOnline(),response.body().get(i).getId()));


                            }

                            resultadapter= new FriendItemAdapterPanel(getContext(),resultlist,mSharedPreference.getToken(getContext()));

                            resultadapter.notifyDataSetChanged();
                            result.setAdapter(resultadapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProfileModel>> call, Throwable t) {

                    }
                });
            }
        });


        return Search;
    }

}
