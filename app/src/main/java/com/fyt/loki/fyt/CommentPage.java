package com.fyt.loki.fyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentPage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProfileInterface profileInterface;

    private int mParam1;
    private String mParam2;
    private String BASE_URL;
    private  String BASE_URL_API;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommentAdapter mCommentAdapter;
    private ArrayList<CommentType> mDataset;

    public CommentPage() {
        // Required empty public constructor
    }


    public static CommentPage newInstance(int param1, String param2) {
        CommentPage fragment = new CommentPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Cview=inflater.inflate(R.layout.fragment_comment_page, container, false);

        BASE_URL= getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        mRecyclerView=(RecyclerView)Cview.findViewById(R.id.commentsRV);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataset=new ArrayList<CommentType>();

        Call<List<CommentModel>> getcomms=profileInterface.getComment(" Token "+mParam2,mParam1);
        getcomms.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if(response.isSuccessful()){
                    mDataset.clear();
                    for (int i = 0; i <response.body().size() ; i++) {

                        mDataset.add(new CommentType(response.body().get(i).author.avatar,response.body().get(i).author.username,response.body().get(i).text,response.body().get(i).created));

                    }
                    //Toast.makeText(getContext(),response.body().get(0).text,Toast.LENGTH_LONG).show();
                    mCommentAdapter = new CommentAdapter(getActivity(),mDataset);
                    mCommentAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mCommentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });





        return  Cview;
    }

}
