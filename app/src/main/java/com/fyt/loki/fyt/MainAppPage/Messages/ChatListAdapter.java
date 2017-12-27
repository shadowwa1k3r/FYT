package com.fyt.loki.fyt.MainAppPage.Messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fyt.loki.fyt.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ergas on 12/16/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChLHolder> {
    private ArrayList<ChatListItemType> mData;
    private Context mContext;
    private String token;
    private String BASE_URL;

    public ChatListAdapter(Context context, ArrayList<ChatListItemType> data){
        this.mContext=context;
        this.mData=data;
    }
    @Override
    public ChatListAdapter.ChLHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ChLHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_list_item,parent,false));
    }
    @Override
    public void onBindViewHolder(final ChLHolder holder,final int position){
        final ChatListItemType currentItem=mData.get(position);
        BASE_URL=mContext.getString(R.string.BASE_URL);
        Picasso.with(mContext).load(currentItem.getImg()).into(holder.ava);

        /*holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)mContext;
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.videocontainer,ChatFrame.newInstance("ergash",currentItem.getName())).addToBackStack(null).commit();
                mData.get(position).setUnreadmsg_count(0);


            }
        });*/
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }



    class ChLHolder extends RecyclerView.ViewHolder{
        private CircleImageView ava;
        private FrameLayout item;
        private TextView user_name,msg,date,unread_count;

        ChLHolder(final View itemview){
            super(itemview);
            item=(FrameLayout)itemview.findViewById(R.id.chat_list_item);
            ava=(CircleImageView)itemview.findViewById(R.id.chat_list_ava);
            user_name=(TextView)itemview.findViewById(R.id.chat_list_user_name);
            msg=(TextView)itemview.findViewById(R.id.chat_list_msg);
            date=(TextView)itemview.findViewById(R.id.chat_list_date);
            unread_count=(TextView)itemview.findViewById(R.id.chat_list_unread_cnt);
        }
        void bindTo(ChatListItemType current){
            user_name.setText(current.getName());
            msg.setText(current.getMsg());
            date.setText(current.getDate());
            if(current.getUnreadmsg_count()>0) unread_count.setText(String.valueOf(current.getUnreadmsg_count()));
            else unread_count.setVisibility(View.INVISIBLE);
        }
    }
}
