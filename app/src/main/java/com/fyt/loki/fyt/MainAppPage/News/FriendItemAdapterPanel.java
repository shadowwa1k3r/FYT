package com.fyt.loki.fyt.MainAppPage.News;

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
 * Created by ergas on 11/15/2017.
 */

public class FriendItemAdapterPanel extends RecyclerView.Adapter<FriendItemAdapterPanel.FVHolder> {

    private ArrayList<FriendItemType> mData;
    private Context mContext;
    private String token;
    private String BASE_URL;

    public FriendItemAdapterPanel(Context context, ArrayList<FriendItemType> data,String token){
        this.mData=data;
        this.mContext=context;
        this.token=token;
    }
    @Override
    public FriendItemAdapterPanel.FVHolder onCreateViewHolder(ViewGroup parent , int viewType){
        return new FVHolder(LayoutInflater.from(mContext).inflate(R.layout.friend_list_item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(final FVHolder holder,final int position){
        final FriendItemType currentFitem = mData.get(position);


        BASE_URL=mContext.getString(R.string.BASE_URL);
        try {
            Glide.with(mContext).load(BASE_URL+currentFitem.getImg()).into(holder.imgv);
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



    class FVHolder extends RecyclerView.ViewHolder{

        private ImageView imgv;
        private TextView txtv;
        private ImageView status;

        FVHolder(final View itemview)
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
