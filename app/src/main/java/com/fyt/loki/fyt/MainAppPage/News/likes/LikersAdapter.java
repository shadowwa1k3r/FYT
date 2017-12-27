package com.fyt.loki.fyt.MainAppPage.News.likes;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendFullInfoPage;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendItemType;
import com.fyt.loki.fyt.R;

import java.util.ArrayList;

/**
 * Created by ergas on 12/25/2017.
 */

public class LikersAdapter extends RecyclerView.Adapter<LikersAdapter.LikeHolder> {
    private ArrayList<FriendItemType> mData;
    private Context mContext;
    private String token;
    private String BASE_URL;

    public LikersAdapter(Context context, ArrayList<FriendItemType> data,String token){
        this.mData=data;
        this.mContext=context;
        this.token=token;
    }
    @Override
    public LikersAdapter.LikeHolder onCreateViewHolder(ViewGroup parent , int viewType){
        return new LikeHolder(LayoutInflater.from(mContext).inflate(R.layout.friend_list_item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(final LikeHolder holder,final int position){
        final FriendItemType currentFitem = mData.get(position);


        BASE_URL=mContext.getString(R.string.BASE_URL);
        try {
            Glide.with(mContext).load(currentFitem.getImg()).into(holder.imgv);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.bindTo(currentFitem);

        final String item = mData.get(position).getName();
        holder.txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.contentContainer, FriendFullInfoPage.newInstance(token,item)).addToBackStack(null).commit();
            }
        });
        holder.imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.contentContainer,FriendFullInfoPage.newInstance(token,item)).addToBackStack(null).commit();

            }
        });
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }



    class LikeHolder extends RecyclerView.ViewHolder{

        private ImageView imgv;
        private TextView txtv;
        private ImageView status;

        LikeHolder(final View itemview)
        {
            super(itemview);
            imgv =(ImageView)itemview.findViewById(R.id.profile_photo);
            txtv = (TextView)itemview.findViewById(R.id.user_name);
            status = (ImageView) itemview.findViewById(R.id.online);
        }

        void  bindTo(FriendItemType current){
            txtv.setText(current.getName());
            if(current.getStatus()){
                status.setVisibility(View.VISIBLE);
            }
            else{
                status.setVisibility(View.GONE);
            }


        }
    }
}
