package com.fyt.loki.fyt.MainAppPage.Menu.Friends;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.MainAppPage.News.Feed.ProfPostsAdapter;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.PostItemModel;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.PostItemType;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.ProfileModel;
import com.fyt.loki.fyt.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendFullInfoPage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ProfileInterface profileInterface;
    private String mToken;
    private String mUserName;
    private String BASE_URL;
    private String BASE_URL_API ;

    private TextView tFull_name,tBirth_date,tGender,tCountry,tCity,tEmail,tPhone,frcount;
    private CircleImageView iAvatar;

    private RecyclerView mRecyclerView_posts;
    private RecyclerView.LayoutManager mLayoutManager_posts;
    private ProfPostsAdapter mAdapter_posts;
    private ArrayList<PostItemType> mDataset_posts;
    private Button follow;
    private boolean friend = false;
    private int user_id;



    public FriendFullInfoPage() {
        // Required empty public constructor
    }


    public static FriendFullInfoPage newInstance(String token, String userName) {
        FriendFullInfoPage fragment = new FriendFullInfoPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, token);
        args.putString(ARG_PARAM2, userName);
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Fpage = inflater.inflate(R.layout.another_friend_info_page, container, false);


        BASE_URL= getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";
        Toast.makeText(getContext(),BASE_URL,Toast.LENGTH_LONG).show();

        tFull_name = (TextView)Fpage.findViewById(R.id.friend_profile_name);
        tBirth_date = (TextView)Fpage.findViewById(R.id.birth_datef);
        tGender = (TextView)Fpage.findViewById(R.id.genderf);
        tCountry = (TextView)Fpage.findViewById(R.id.countryf);
        tCity = (TextView)Fpage.findViewById(R.id.cityf);
        tEmail = (TextView)Fpage.findViewById(R.id.emailf);
        tPhone = (TextView)Fpage.findViewById(R.id.phonef);
        iAvatar =(CircleImageView) Fpage.findViewById(R.id.friend_profile_photo);
        frcount=(TextView)Fpage.findViewById(R.id.friends_count);
        follow=(Button)Fpage.findViewById(R.id.follow);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        mRecyclerView_posts = (RecyclerView)Fpage.findViewById(R.id.PostsRv);
        mRecyclerView_posts.setHasFixedSize(true);

        mLayoutManager_posts = new LinearLayoutManager(getActivity());
        mRecyclerView_posts.setLayoutManager(mLayoutManager_posts);

        mDataset_posts = new ArrayList<PostItemType>();

        final Call<List<FriendInfoModel>> friends = profileInterface.friendInfo(" Token "+mToken);

        friends.enqueue(new Callback<List<FriendInfoModel>>() {
            @Override
            public void onResponse(Call<List<FriendInfoModel>> call, Response<List<FriendInfoModel>> response) {
                if (response.isSuccessful()){
                    for (int i = 0; i <response.body().size() ; i++) {
                        if (mUserName.equals(response.body().get(i).username)) {
                            follow.setText("Unfollow - ");
                            friend=true;
                        }
                        else {
                            follow.setText("Follow + ");
                            friend=false;
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<FriendInfoModel>> call, Throwable t) {

            }
        });




        final AlertDialog dialog = new SpotsDialog(getContext());
        dialog.show();

        Call<ProfileModel> fprofileInfo = profileInterface.profileInfo(" Token "+mToken,mUserName);
        fprofileInfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {

                if (response.isSuccessful())
                {
                    try {
                        Glide.with(getActivity()).load(response.body().getProfile().getAvatar()).asBitmap().animate(R.anim.zoom_in).into(iAvatar);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    final String ava = BASE_URL+response.body().getProfile().getAvatar();


                    tFull_name.setText(response.body().getUsername());
                    tBirth_date.setText(response.body().getProfile().getBirthDate());
                    tCountry.setText(response.body().getProfile().getCountry());
                    if (response.body().getProfile().getGender()==1){
                        tGender.setText("Male");}
                    else if(response.body().getProfile().getGender()==0){
                        tGender.setText("Female");
                    }
                    user_id=response.body().getId();
                    tCity.setText(response.body().getProfile().getCity());
                    tEmail.setText(response.body().getEmail());
                    tPhone.setText(response.body().getProfile().getPhone());
                    frcount.setText(response.body().getProfile().getFriendsCount().toString());
                    //Toast.makeText(getContext(),response.body().getUsername(),Toast.LENGTH_LONG).show();

                    FriendPostBody body = new FriendPostBody();
                    body.username = mUserName;
                    Call<List<PostItemModel>> getFreindPosts = profileInterface.getFriendPosts(" Token "+mToken,body);
                    getFreindPosts.enqueue(new Callback<List<PostItemModel>>() {
                        @Override
                        public void onResponse(Call<List<PostItemModel>> call, Response<List<PostItemModel>> response) {
                            if(response.isSuccessful()){
                                mDataset_posts.clear();

                                for (int i = 0; i <response.body().size() ; i++) {
                                    mDataset_posts.add(new PostItemType(response.body().get(i).id,ava,mUserName,response.body().get(i).created,response.body().get(i).context,
                                            response.body().get(i).likes_count,response.body().get(i).comments,response.body().get(i).images,response.body().get(i).videos,response.body().get(i).likes));
                                   // Toast.makeText(getContext(),i,Toast.LENGTH_SHORT).show();
                                }
                                mAdapter_posts=new ProfPostsAdapter(inflater.getContext(),mDataset_posts,mToken,mUserName);
                                mRecyclerView_posts.setAdapter(mAdapter_posts);



                            }
                        }

                        @Override
                        public void onFailure(Call<List<PostItemModel>> call, Throwable t) {

                        }
                    });

                    dialog.dismiss();
                }
                else
                    dialog.dismiss();


            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                dialog.dismiss();

            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!friend){
                    FollowBody body=new FollowBody();
                    body.user=user_id;
                Call<FollowResponse> followUser=profileInterface.follow(" Token "+mToken,body);
                followUser.enqueue(new Callback<FollowResponse>() {
                    @Override
                    public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                        if(response.isSuccessful()){

                                follow.setText("Unfollow - ");
                                friend=true;

                        }
                    }

                    @Override
                    public void onFailure(Call<FollowResponse> call, Throwable t) {

                    }
                });}
                else {
                    FollowBody body=new FollowBody();
                    body.user=user_id;
                    Call<FollowResponse> unFollowUser=profileInterface.unfollow(" Token "+mToken,body);
                    unFollowUser.enqueue(new Callback<FollowResponse>() {
                        @Override
                        public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                            if (response.isSuccessful()){
                                follow.setText("Follow + ");
                                friend=false;
                            }
                        }

                        @Override
                        public void onFailure(Call<FollowResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });



        // Inflate the layout for this fragment
        return Fpage;
    }

}
