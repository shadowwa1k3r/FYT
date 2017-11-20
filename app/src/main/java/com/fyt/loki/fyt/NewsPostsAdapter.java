package com.fyt.loki.fyt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ergas on 11/18/2017.
 */

public class NewsPostsAdapter extends RecyclerView.Adapter<NewsPostsAdapter.NFVHolder> {

    private ArrayList<NewsFeedItemType> mData;
    private Context mContext;
    private String BASE_URL;

    NewsPostsAdapter(Context context, ArrayList data){
        this.mData=data;
        this.mContext=context;
    }

    @Override
    public NewsPostsAdapter.NFVHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new NFVHolder(LayoutInflater.from(mContext).inflate(R.layout.news_feed_item,parent,false));
    }
    @Override
    public void onBindViewHolder(final NFVHolder holder,final int position){
        final  NewsFeedItemType currentItem = mData.get(position);
        Glide.with(mContext).load(currentItem.getAvatar()).asBitmap().into(holder.avatar);
        final String [] post_imgs = new String[currentItem.getImages().size()];

        BASE_URL= "http://192.168.1.104:8000";

        for (int i = 0; i <currentItem.getImages().size() ; i++) {
            post_imgs[i] = BASE_URL+currentItem.getImages().get(i).photo;

        }
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(mContext).load(post_imgs[position]).into(imageView);
            }
        };

        holder.post_img.setImageListener(imageListener);



        holder.bindTo(currentItem);

    }
    @Override
    public int getItemCount(){
        return mData.size();
    }

    class NFVHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView username,createdAt,postTXT,likeCount,commentCount;
        private CarouselView post_img;


        NFVHolder(final View itemview){
            super(itemview);
            avatar = (CircleImageView)itemview.findViewById(R.id.post_avaf);
            username = (TextView)itemview.findViewById(R.id.post_usernamef);
            createdAt = (TextView)itemview.findViewById(R.id.post_createdAtf);
            postTXT = (TextView)itemview.findViewById(R.id.post_txtf);
            likeCount = (TextView)itemview.findViewById(R.id.likef);
            commentCount = (TextView)itemview.findViewById(R.id.commentf);
            post_img = (CarouselView)itemview.findViewById(R.id.post_imgf);
        }

        void bindTo(NewsFeedItemType current){
            post_img.setPageCount(current.getImages().size());

            username.setText(current.getUsername());
            createdAt.setText(current.getCreatedAt());
            postTXT.setText(current.getPostTXT());
            likeCount.setText(current.getLikeCount());
            commentCount.setText(current.getCommentCount());


        }



    }





}
