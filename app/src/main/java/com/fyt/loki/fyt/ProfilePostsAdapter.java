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

public class ProfilePostsAdapter extends RecyclerView.Adapter<ProfilePostsAdapter.PPVHolder> {

    private ArrayList<PostItemType> mData;
    private Context mContext;
    private String BASE_URL;

    ProfilePostsAdapter(Context context,ArrayList data){
        this.mData=data;
        this.mContext=context;
    }

    @Override
    public ProfilePostsAdapter.PPVHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new PPVHolder(LayoutInflater.from(mContext).inflate(R.layout.post_item,parent,false));
    }
    @Override
    public void onBindViewHolder(final PPVHolder holder,final int position){
        final  PostItemType currentItem = mData.get(position);
        Glide.with(mContext).load(currentItem.getAvatar()).asBitmap().into(holder.avatar);
        final String [] post_imgs = new String[currentItem.getImages().size()];

        BASE_URL= "http://192.168.1.103:8000";

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

    class PPVHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView username,createdAt,postTXT,likeCount,commentCount;
        private CarouselView post_img;


        PPVHolder(final View itemview){
            super(itemview);
            avatar = (CircleImageView)itemview.findViewById(R.id.post_ava);
            username = (TextView)itemview.findViewById(R.id.post_username);
            createdAt = (TextView)itemview.findViewById(R.id.post_createdAt);
            postTXT = (TextView)itemview.findViewById(R.id.post_txt);
            likeCount = (TextView)itemview.findViewById(R.id.like_count);
            commentCount = (TextView)itemview.findViewById(R.id.comment_count);
            post_img = (CarouselView)itemview.findViewById(R.id.post_img);
        }

        void bindTo(PostItemType current){
            post_img.setPageCount(current.getImages().size());

            username.setText(current.getUsername());
            createdAt.setText(current.getCreatedAt());
            postTXT.setText(current.getPostTXT());
            likeCount.setText(current.getLikeCount());
            commentCount.setText(current.getCommentCount());


        }



    }





}
