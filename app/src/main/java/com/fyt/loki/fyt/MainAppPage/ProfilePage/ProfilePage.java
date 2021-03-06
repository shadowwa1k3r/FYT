package com.fyt.loki.fyt.MainAppPage.ProfilePage;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendInfoModel;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendItemType;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendPostBody;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendsListAll;
import com.fyt.loki.fyt.MainAppPage.News.Feed.ProfPostsAdapter;
import com.fyt.loki.fyt.MainAppPage.News.FriendItemAdapter;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ProfileInterface;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfilePage extends Fragment  {



    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private String username,first_name,last_name,full_name,email,birth_date,country,city,avatar,gender,phone,address;

    private CircleImageView iAvatar2,iAvatar;
    private TextView tFull_name,tBirth_date,tGender,tCountry,tCity,tEmail,tPhone;
    private Button edit,list_friends,activity_log;
    private ImageView status,editava;
  //  private myCustomPane friendsPanel;
  //  private Button hidepanel;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FriendItemAdapter mAdapter;
    private ArrayList<FriendItemType> mDataset;
    private TextView friends,frcount;

 /*   private RecyclerView mRecyclerView_panel;
    private RecyclerView.LayoutManager mLayoutManager_panel;
    private FriendItemAdapterPanel mAdapter_panel;
    private ArrayList<FriendItemType> mDataset_panel;*/

    private RecyclerView mRecyclerView_posts;
    private RecyclerView.LayoutManager mLayoutManager_posts;
    private ProfPostsAdapter mAdapter_posts;
    private ArrayList<PostItemType> mDataset_posts;




    private  String BASE_URL ;
    private  String BASE_URL_API ;

     public ProfileInterface profileInterface;

    private static String mParam1,ARG_PARAM1="param1";
    private static String mParam2,ARG_PARAM2="param2";
    public ProfilePage() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfilePage newInstance(String token,String user_name) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,token);
        args.putString(ARG_PARAM2,user_name);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View Ppage=inflater.inflate(R.layout.another_profile_page, container, false);

        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        BASE_URL = getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";


        tFull_name = (TextView)Ppage.findViewById(R.id.user_profile_name);
        tBirth_date = (TextView)Ppage.findViewById(R.id.birth_date2);
        tGender = (TextView)Ppage.findViewById(R.id.gender2);
        tCountry = (TextView)Ppage.findViewById(R.id.country2);
        tCity = (TextView)Ppage.findViewById(R.id.city2);
        tEmail = (TextView)Ppage.findViewById(R.id.email2);
        tPhone = (TextView)Ppage.findViewById(R.id.phone2);
        iAvatar =(CircleImageView) Ppage.findViewById(R.id.user_profile_photo);
        iAvatar2 =(CircleImageView)Ppage.findViewById(R.id.myava2);
       // hidepanel = (Button)Ppage.findViewById(R.id.hide_panel);
        friends = (TextView)Ppage.findViewById(R.id.friend_count);
        frcount = (TextView)Ppage.findViewById(R.id.friends_count);
        activity_log = (Button)Ppage.findViewById(R.id.activity_log);
        editava=(ImageView)Ppage.findViewById(R.id.edit_ava);



        edit = (Button)Ppage.findViewById(R.id.profile_edit);
        list_friends=(Button)Ppage.findViewById(R.id.see_all_friends);


      //  friendsPanel = (myCustomPane)Ppage.findViewById(R.id.pane2);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         profileInterface = retrofit.create(ProfileInterface.class);



        //bindActivity(Ppage);

        //mAppBarLayout.addOnOffsetChangedListener(this);

       // mToolbar.inflateMenu(R.menu.menu_main);
      //  startAlphaAnimation(mTitle, 0, View.INVISIBLE);
       // tPhone.setText("Token "+mParam1);

        mRecyclerView = (RecyclerView)Ppage.findViewById(R.id.friends_rv2);
        mRecyclerView.setHasFixedSize(true);

       /* mRecyclerView_panel=(RecyclerView)Ppage.findViewById(R.id.rv_panel2);
        mRecyclerView_panel.setHasFixedSize(true);*/

        mRecyclerView_posts = (RecyclerView)Ppage.findViewById(R.id.PostsRv);
        mRecyclerView_posts.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);

       /* mLayoutManager_panel = new LinearLayoutManager(getActivity());
        mRecyclerView_panel.setLayoutManager(mLayoutManager_panel);*/

        mLayoutManager_posts = new LinearLayoutManager(getActivity());
        mRecyclerView_posts.setLayoutManager(mLayoutManager_posts);

        mDataset = new ArrayList<FriendItemType>();
      //  mDataset_panel = new ArrayList<FriendItemType>();
        mDataset_posts = new ArrayList<PostItemType>();

        final View inflatedView = getActivity().getLayoutInflater().inflate(R.layout.friend_item, null);
         status = (ImageView) inflatedView.findViewById(R.id.online);


         editava.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                         .replace(R.id.contentContainer, new EditProfileAva()).addToBackStack(null).commit();
             }
         });
        list_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                       .replace(R.id.contentContainer, new FriendsListAll()).addToBackStack(null).commit();
            }
        });


                final AlertDialog dialog = new SpotsDialog(getContext());
                dialog.show();

                Call<ProfileModel> mprofileInfo = profileInterface.profileInfo(" Token "+mParam1,mParam2);
                mprofileInfo.enqueue(new Callback<ProfileModel>() {
                    @Override
                    public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                        if(response.isSuccessful()){
                            try {
                                Glide.with(getActivity()).load( response.body().getProfile().getAvatar()).asBitmap().animate(R.anim.zoom_in).into(iAvatar);
                                Glide.with(inflater.getContext()).load(response.body().getProfile().getAvatar()).asBitmap().animate(R.anim.zoom_in).into(iAvatar2);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            final String ava =BASE_URL+ response.body().getProfile().getAvatar();
                            final String usnm = response.body().getUsername();

                            tFull_name.setText(response.body().getFirstName()+" "+response.body().getLastName());
                            tBirth_date.setText(response.body().getProfile().getBirthDate());
                            if (response.body().getProfile().getGender()==1){
                            tGender.setText("Male");}
                            else if(response.body().getProfile().getGender()==0){
                                tGender.setText("Female");
                            }
                            tCountry.setText(response.body().getProfile().getCountry());
                            tCity.setText(response.body().getProfile().getCity());
                            tEmail.setText(response.body().getEmail());
                            tPhone.setText(response.body().getProfile().getPhone());

                            Call<List<FriendInfoModel>> mFriendInfo = profileInterface.friendInfo(" Token " +mParam1);
                            mFriendInfo.enqueue(new Callback<List<FriendInfoModel>>() {
                                @Override
                                public void onResponse(Call<List<FriendInfoModel>> call, Response<List<FriendInfoModel>> response) {
                                    if(response.isSuccessful()){
                                        mDataset.clear();
                                      //  mDataset_panel.clear();


                                        for (int i=0; i<response.body().size();i++){
                                          //  mDataset_panel.add(new FriendItemType(response.body().get(i).getAvatar(), response.body().get(i).getUsername(), response.body().get(i).getIs_online()));
                                            if(response.body().get(i).profile.is_online) {
                                                mDataset.add(new FriendItemType(response.body().get(i).profile.avatar, response.body().get(i).username, response.body().get(i).profile.is_online,response.body().get(i).id));

                                            }


                                        }
                                        friends.setText("Friends("+response.body().size()+")");
                                        frcount.setText(String.valueOf(response.body().size()));
                                        mAdapter = new FriendItemAdapter(getActivity(),mDataset);
                                     //   mAdapter_panel = new FriendItemAdapterPanel(getActivity(),mDataset_panel,mParam1);

                                        mRecyclerView.setAdapter(mAdapter);
                                     //   mRecyclerView_panel.setAdapter(mAdapter_panel);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<FriendInfoModel>> call, Throwable t) {

                                }
                            });

                            FriendPostBody body = new FriendPostBody();
                            body.username = response.body().getUsername();
                            final Call<List<PostItemModel>> getFriendPosts = profileInterface.getFriendPosts(" Token "+mParam1,body);
                            getFriendPosts.enqueue(new Callback<List<PostItemModel>>() {
                                @Override
                                public void onResponse(Call<List<PostItemModel>> call, Response<List<PostItemModel>> response) {
                                    if(response.isSuccessful()){
                                    mDataset_posts.clear();

                                    for (int i = 0; i <response.body().size() ; i++) {
                                        mDataset_posts.add(new PostItemType(response.body().get(i).id,response.body().get(i).owner.profile.avatar,response.body().get(i).owner.username,response.body().get(i).created,response.body().get(i).context,
                                                response.body().get(i).likes_count,response.body().get(i).comments,response.body().get(i).images,response.body().get(i).videos,response.body().get(i).likes));
                                       // Toast.makeText(getContext(),i,Toast.LENGTH_SHORT).show();
                                    }
                                        mAdapter_posts=new ProfPostsAdapter(getActivity(),mDataset_posts,mParam1,mParam2);
                                        mRecyclerView_posts.setAdapter(mAdapter_posts);


                                    }

                                }

                                @Override
                                public void onFailure(Call<List<PostItemModel>> call, Throwable t) {

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
                activity_log.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.contentContainer,new ActivityLog()).addToBackStack(null).commit();

                    }
                });




        return Ppage;
    }





}
