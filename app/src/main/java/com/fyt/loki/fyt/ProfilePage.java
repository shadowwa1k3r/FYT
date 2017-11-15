package com.fyt.loki.fyt;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfilePage extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private String username,first_name,last_name,full_name,email,birth_date,country,city,avatar,gender,phone,address;

    private CircleImageView iAvatar,iAvatar2;
    private TextView tFull_name,tBirth_date,tGender,tCountry,tCity,tEmail,tPhone;
    private Button edit,status;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FriendItemAdapter mAdapter;
    private ArrayList<FriendItemType> mDataset;


    String BASE_URL = "http://192.168.1.103:8000/";
    String BASE_URL_API =BASE_URL+"api/";

     public ProfileInterface profileInterface;

    private static String mParam1,ARG_PARAM1="param1";
    public ProfilePage() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfilePage newInstance(String token) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,token);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View Ppage=inflater.inflate(R.layout.fragment_profile_page, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        tFull_name = (TextView)Ppage.findViewById(R.id.full_name);
        tBirth_date = (TextView)Ppage.findViewById(R.id.birth_date);
        tGender = (TextView)Ppage.findViewById(R.id.gender);
        tCountry = (TextView)Ppage.findViewById(R.id.country);
        tCity = (TextView)Ppage.findViewById(R.id.city);
        tEmail = (TextView)Ppage.findViewById(R.id.email);
        tPhone = (TextView)Ppage.findViewById(R.id.phone);
        iAvatar =(CircleImageView)Ppage.findViewById(R.id.avatar);
        iAvatar2 =(CircleImageView)Ppage.findViewById(R.id.myava);
        edit = (Button)Ppage.findViewById(R.id.edit);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         profileInterface = retrofit.create(ProfileInterface.class);



        bindActivity(Ppage);

        mAppBarLayout.addOnOffsetChangedListener(this);

        mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
       // tPhone.setText("Token "+mParam1);

        mRecyclerView = (RecyclerView)Ppage.findViewById(R.id.friends_rv);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataset = new ArrayList<FriendItemType>();

        View inflatedView = getActivity().getLayoutInflater().inflate(R.layout.friend_item, null);
         status = (Button) inflatedView.findViewById(R.id.button8);





                final AlertDialog dialog = new SpotsDialog(getContext());
                dialog.show();

                Call<ProfileModel> mprofileInfo = profileInterface.profileInfo(" Token "+mParam1);
                mprofileInfo.enqueue(new Callback<ProfileModel>() {
                    @Override
                    public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                        if(response.isSuccessful()){
                            Glide.with(getActivity()).load(response.body().getAvatar()).asBitmap().into(iAvatar);
                            Glide.with(getActivity()).load(response.body().getAvatar()).asBitmap().into(iAvatar2);
                            tFull_name.setText(response.body().getFull_name());
                            tBirth_date.setText(response.body().getBith_date());
                            tGender.setText(response.body().getGender());
                            tCountry.setText(response.body().getCountry());
                            tCity.setText(response.body().getCity());
                            tEmail.setText(response.body().getEmail());
                            tPhone.setText(response.body().getPhone());

                            Call<List<FriendInfoModel>> mFriendInfo = profileInterface.friendInfo(" Token " +mParam1);
                            mFriendInfo.enqueue(new Callback<List<FriendInfoModel>>() {
                                @Override
                                public void onResponse(Call<List<FriendInfoModel>> call, Response<List<FriendInfoModel>> response) {
                                    if(response.isSuccessful()){
                                        mDataset.clear();
                                        for (int i=0; i<response.body().size();i++){
                                            mDataset.add(new FriendItemType(response.body().get(i).getAvatar(),response.body().get(i).getUsername(),response.body().get(i).getIs_online()));


                                        }
                                        mAdapter = new FriendItemAdapter(getActivity(),mDataset);

                                        mRecyclerView.setAdapter(mAdapter);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<FriendInfoModel>> call, Throwable t) {

                                }
                            });

                            dialog.dismiss();
                        }
                        else {


                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileModel> call, Throwable t) {


                        dialog.dismiss();

                    }
                });




        return Ppage;
    }






    private void bindActivity(View v) {
        mToolbar        = (Toolbar) v.findViewById(R.id.main_toolbar);
        mTitle          = (TextView) v.findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) v.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) v.findViewById(R.id.main_appbar);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }




}
