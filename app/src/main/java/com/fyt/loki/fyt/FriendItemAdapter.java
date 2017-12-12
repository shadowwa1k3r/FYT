package com.fyt.loki.fyt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by ergas on 11/15/2017.
 */

public class FriendItemAdapter extends RecyclerView.Adapter<FriendItemAdapter.FVHolder> {

    private ArrayList<FriendItemType> mData;
    private Context mContext;
    private String BASE_URL;

    FriendItemAdapter(Context context,ArrayList<FriendItemType> data){
        this.mData=data;
        this.mContext=context;
    }
    @Override
    public FriendItemAdapter.FVHolder onCreateViewHolder(ViewGroup parent ,int viewType){
        return new FVHolder(LayoutInflater.from(mContext).inflate(R.layout.friend_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final FVHolder holder,final int position){
        final FriendItemType currentFitem = mData.get(position);

        BASE_URL = mContext.getString(R.string.BASE_URL);
        try {
            Glide.with(mContext).load(BASE_URL+currentFitem.getImg()).into(holder.imgv);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.bindTo(currentFitem);
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
            imgv =(ImageView)itemview.findViewById(R.id.friend_item);
            txtv = (TextView)itemview.findViewById(R.id.FriendFullName);
            status = (ImageView) itemview.findViewById(R.id.online);
        }

        void  bindTo(FriendItemType current){
            txtv.setText(current.getName());
            if(current.getStatus()==true){
                status.setVisibility(View.VISIBLE);
            }
            else{
                status.setVisibility(View.GONE);
            }


        }
    }

}
