package com.fyt.loki.fyt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ergas on 11/28/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CHolder> {

    private ArrayList<CommentType> mData;
    private Context mContext;
    private String BASE_URL;

    CommentAdapter(Context context,ArrayList data){
        this.mData=data;
        this.mContext=context;
    }

    @Override
    public CommentAdapter.CHolder onCreateViewHolder(ViewGroup parent,int viewType){
        return new CHolder(LayoutInflater.from(mContext).inflate(R.layout.comment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final CHolder holder,final int position){
        final CommentType currentItem=mData.get(position);

        BASE_URL=mContext.getString(R.string.BASE_URL);
        Glide.with(mContext).load(BASE_URL+currentItem.getAva()).animate(R.anim.zoom_in).into(holder.avatar);

        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    class CHolder extends RecyclerView.ViewHolder{
        private CircleImageView avatar;
        private TextView username,comment,date;

        CHolder(final View itemview){
            super(itemview);
            avatar=(CircleImageView)itemview.findViewById(R.id.commentava);
            username=(TextView)itemview.findViewById(R.id.commentUser);
            comment=(TextView)itemview.findViewById(R.id.commentTXT);
            date=(TextView)itemview.findViewById(R.id.commentTIME);

        }

        void bindTo(CommentType current){
           // Toast.makeText(mContext,current.getComment(),Toast.LENGTH_LONG).show();
            username.setText(current.getUsername());
            comment.setText(current.getComment());
            date.setText(current.getTime());
        }
    }
}
