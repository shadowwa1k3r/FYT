package com.fyt.loki.fyt;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentPage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private ProfileInterface profileInterface;

    private int post_id;
    private String token,username,avatar;
    private String BASE_URL;
    private  String BASE_URL_API;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommentAdapter mCommentAdapter;
    private ArrayList<CommentType> mDataset;

    private ImageButton sendComment;
    private EditText commentText;
    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime().withZoneUTC();

    public CommentPage() {
        // Required empty public constructor
    }


    public static CommentPage newInstance(int param1, String param2,String param3) {
        CommentPage fragment = new CommentPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3,param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post_id = getArguments().getInt(ARG_PARAM1);
            token = getArguments().getString(ARG_PARAM2);
            username=getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Cview=inflater.inflate(R.layout.fragment_comment_page, container, false);


        BASE_URL= getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        sendComment = (ImageButton)Cview.findViewById(R.id.sendComment);
        commentText = (EditText)Cview.findViewById(R.id.CommentEditText);

        mRecyclerView=(RecyclerView)Cview.findViewById(R.id.commentsRV);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataset=new ArrayList<CommentType>();

        Call<List<CommentModel>> getcomms=profileInterface.getComment(" Token "+token,post_id);
        getcomms.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if(response.isSuccessful()){
                    mDataset.clear();
                    for (int i = 0; i <response.body().size() ; i++) {

                        mDataset.add(new CommentType(response.body().get(i).author.avatar,response.body().get(i).author.username,response.body().get(i).text,response.body().get(i).created,response.body().get(i).id));

                    }
                    //Toast.makeText(getContext(),response.body().get(0).text,Toast.LENGTH_LONG).show();
                    mCommentAdapter = new CommentAdapter(inflater.getContext(),mDataset,token);
                    mCommentAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mCommentAdapter);
                    FrameLayout fl=(FrameLayout)getActivity().findViewById(R.id.mainFrame);
                    fl.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });
        Call<ProfileModel> pinfo=profileInterface.profileInfo(" Token "+token,username);
        pinfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    avatar=response.body().getProfile().getAvatar();
                }
            }
            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });
        final commentBody body = new commentBody();
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body.text=commentText.getText().toString();
                body.post=post_id;
                Call<commentResponse> comment = profileInterface.comment(" Token "+token,body);
                comment.enqueue(new Callback<commentResponse>() {
                    @Override
                    public void onResponse(Call<commentResponse> call, Response<commentResponse> response) {
                        if(response.isSuccessful())
                        {
                            Random random=new Random();
                            DateTime dt=new DateTime(DateTimeZone.UTC);
                            mDataset.add(new CommentType(avatar,username,body.text,jodaDateTimeToIsoString(dt), response.body().id));
                            mCommentAdapter.notifyDataSetChanged();
                            commentText.setText("");
                            mLayoutManager.scrollToPosition(mDataset.size()-1);
                            InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                        }
                        else commentText.setText("responseerror");
                    }

                    @Override
                    public void onFailure(Call<commentResponse> call, Throwable t) {
                        commentText.setText("unexpectedError");

                    }
                });

            }
        });







        return  Cview;
    }
    public static String jodaDateTimeToIsoString(DateTime dateTime) {
        String dateTimeString = ISO_DATE_TIME_FORMATTER.print(dateTime);
        return dateTimeString;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        FrameLayout fl=(FrameLayout)getActivity().findViewById(R.id.mainFrame);
        fl.setVisibility(View.VISIBLE);
    }


}
