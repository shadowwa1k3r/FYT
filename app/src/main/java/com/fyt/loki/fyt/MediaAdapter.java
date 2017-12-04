package com.fyt.loki.fyt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by ergas on 12/2/2017.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaHolder> {
    private ArrayList<String> filepath;
    private Context mContext;
    private String BASE_URL;

    MediaAdapter(Context context,ArrayList data){
        this.filepath=data;
        this.mContext=context;
    }

    @Override
    public MediaAdapter.MediaHolder onCreateViewHolder(ViewGroup parent,int viewType){
        return new MediaHolder(LayoutInflater.from(mContext).inflate(R.layout.media_item,parent,false));
    }
    @Override
    public void onBindViewHolder(final MediaHolder holder,final int position){
        final String currentitem = filepath.get(position);
        BASE_URL=mContext.getString(R.string.BASE_URL);

        Glide.with(mContext).load(currentitem).fitCenter().into(holder.media);
    }
    @Override
    public int getItemCount(){
        return filepath.size();
    }

    class MediaHolder extends RecyclerView.ViewHolder{
        private ImageView media;


        MediaHolder(final View itemview){
            super(itemview);
            media=(ImageView)itemview.findViewById(R.id.mediathumb);


        }

        void bindTo(String current){


        }

    }
}
