package com.fyt.loki.fyt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by ergas on 11/15/2017.
 */

public class FriendItemAdapterPanel extends RecyclerView.Adapter<FriendItemAdapterPanel.FVHolder> {

    private ArrayList<FriendItemType> mData;
    private Context mContext;
    private String token;
    private myCustomPane panel;
    String BASE_URL ;

    FriendItemAdapterPanel(Context context, ArrayList<FriendItemType> data,String token){
        this.mData=data;
        this.mContext=context;
        this.token=token;
    }
    @Override
    public FriendItemAdapterPanel.FVHolder onCreateViewHolder(ViewGroup parent , int viewType){
        return new FVHolder(LayoutInflater.from(mContext).inflate(R.layout.friend_item_panel,parent,false));
    }

    @Override
    public void onBindViewHolder(final FVHolder holder,final int position){
        final FriendItemType currentFitem = mData.get(position);
        BASE_URL= "http://192.168.1.106:8000";
        Glide.with(mContext).load(BASE_URL+currentFitem.getImg()).into(holder.imgv);
        holder.bindTo(currentFitem);

        final String item = mData.get(position).getName();
        holder.txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                View inflatedView = activity.getLayoutInflater().inflate(R.layout.another_profile_page,null);
                panel = (myCustomPane)inflatedView.findViewById(R.id.pane2);
                panel.closePane();
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright)
                        .replace(R.id.loginPageContainer,FriendFullInfoPage.newInstance(token,item)).addToBackStack(null).commit();
            }
        });
        holder.imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                View inflatedView = activity.getLayoutInflater().inflate(R.layout.another_profile_page,null);
                panel = (myCustomPane)inflatedView.findViewById(R.id.pane2);
                panel.closePane();
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright)
                        .replace(R.id.loginPageContainer,FriendFullInfoPage.newInstance(token,item)).addToBackStack(null).commit();

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
        private Button status;

        FVHolder(final View itemview)
        {
            super(itemview);
            imgv =(ImageView)itemview.findViewById(R.id.friend_item_panel);
            txtv = (TextView)itemview.findViewById(R.id.FriendFullName_panel);
            status = (Button)itemview.findViewById(R.id.status_friend);
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
