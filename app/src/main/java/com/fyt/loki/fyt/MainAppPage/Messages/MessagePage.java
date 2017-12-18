package com.fyt.loki.fyt.MainAppPage.Messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyt.loki.fyt.R;

import java.util.ArrayList;

public class MessagePage extends Fragment {
    private boolean mConnected;
    private RecyclerView roomlist;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ChatListItemType> rooms;
    private ChatListAdapter mChatListAdapter;



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
        roomlist=(RecyclerView)MP.findViewById(R.id.room_list);
        roomlist.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        roomlist.setLayoutManager(mLayoutManager);





        rooms=new ArrayList<>();
        rooms.add(new ChatListItemType("https://findyourtraining.com/media/default/no_photo_male.png","farrukh","new msg","11:41",2));

        mChatListAdapter=new ChatListAdapter(getActivity(),rooms);
        mChatListAdapter.notifyDataSetChanged();
        roomlist.setAdapter(mChatListAdapter);








        return MP;
    }



}
