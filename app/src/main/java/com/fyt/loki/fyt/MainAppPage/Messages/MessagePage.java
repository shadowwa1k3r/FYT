package com.fyt.loki.fyt.MainAppPage.Messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendInfoModel;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendItemType;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.ProfileModel;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.Tools.RecyclerItemClickListener;
import com.fyt.loki.fyt.Tools.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagePage extends Fragment {
    private boolean mConnected;
    private RecyclerView roomlist;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ChatListItemType> rooms;
    private ChatListAdapter mChatListAdapter;
    private String BASE_URL,BASE_URL_API;
    private ProfileInterface mProfileInterface;
    private ArrayList<FriendItemType> friendList;
    private SharedPreference mSharedPreference;
    private String my_ava;

    public MessagePage() {
        // Required empty public constructor
    }


    public static MessagePage newInstance() {
        MessagePage fragment = new MessagePage();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View MP=inflater.inflate(R.layout.chat_list, container, false);

        mSharedPreference=new SharedPreference();

        BASE_URL = getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mProfileInterface = retrofit.create(ProfileInterface.class);

        friendList=new ArrayList<FriendItemType>();



        roomlist=(RecyclerView)MP.findViewById(R.id.room_list);
        roomlist.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        roomlist.setLayoutManager(mLayoutManager);

        rooms=new ArrayList<>();

        Call<List<FriendInfoModel>> mFriendInfo = mProfileInterface.friendInfo(" Token " +mSharedPreference.getToken(getContext()));
        mFriendInfo.enqueue(new Callback<List<FriendInfoModel>>() {
            @Override
            public void onResponse(Call<List<FriendInfoModel>> call, Response<List<FriendInfoModel>> response) {
                if(response.isSuccessful()) {
                    friendList.clear();
                    rooms.clear();
                    for (int i = 0; i < response.body().size(); i++) {

                        friendList.add(new FriendItemType(response.body().get(i).profile.avatar, response.body().get(i).username, response.body().get(i).profile.is_online, response.body().get(i).id));
                        rooms.add(new ChatListItemType(friendList.get(i).getImg(), friendList.get(i).getName(), "new msg", "11:41", 2));


                    }
                    mChatListAdapter=new ChatListAdapter(getActivity(),rooms);
                    mChatListAdapter.notifyDataSetChanged();
                    roomlist.setAdapter(mChatListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<FriendInfoModel>> call, Throwable t) {

            }
        });





        Call<ProfileModel> mprofileInfo = mProfileInterface.profileInfo(" Token "+mSharedPreference.getToken(getContext()),mSharedPreference.getUserName(getContext()));
        mprofileInfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    my_ava=response.body().getProfile().getAvatar();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });

        final RoomBody room=new RoomBody();

        roomlist.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), roomlist, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                room.user1=mSharedPreference.getUserName(getContext());
                room.user2=friendList.get(position).getName();

                Call<RoomResponse> RoomInfo = mProfileInterface.getRoomInfo(" Token " + mSharedPreference.getToken(getContext()),room);
                RoomInfo.enqueue(new Callback<RoomResponse>() {
                    @Override
                    public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                        getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.videocontainer,ChatFrame.newInstance(mSharedPreference.getUserName(getContext()),rooms.get(position).getName(),response.body().room,my_ava))
                                .addToBackStack(null).commit();
                        rooms.get(position).setUnreadmsg_count(0);
                        mChatListAdapter.notifyItemChanged(position);
                    }

                    @Override
                    public void onFailure(Call<RoomResponse> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));



        return MP;
    }



}
