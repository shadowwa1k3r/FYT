package com.fyt.loki.fyt.MainAppPage.ProfilePage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.Tools.ImageViewer;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.VideoPlayer;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ergas on 11/18/2017.
 */

public class ProfilePostsAdapter extends RecyclerView.Adapter<ProfilePostsAdapter.PPVHolder> {

    private ArrayList<PostItemType> mData;
    private Context mContext;
    private  String  BASE_URL;

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

        final LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BASE_URL=mContext.getString(R.string.BASE_URL);
        try {
            Glide.with(mContext).load(currentItem.getAvatar()).asBitmap().into(holder.avatar);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        final String [] post_tmb = new String[currentItem.getImages().size()];
        final String [] post_media = new String[currentItem.getVideos().size()+currentItem.getImages().size()];





        for (int i = 0; i <currentItem.getImages().size() ; i++) {
            post_media[i] = BASE_URL+currentItem.getImages().get(i).photo;

        }

        int t =0;
        for (int i = currentItem.getImages().size(); i <currentItem.getVideos().size()+currentItem.getImages().size() ; i++) {

            post_media[i] = BASE_URL+currentItem.getVideos().get(t).video;

            t++;

        }
        for (int i = 0; i <currentItem.getVideos().size() ; i++) {
            post_tmb[i] = BASE_URL+currentItem.getVideos().get(i).thumbnail;

        }
        if (currentItem.getVideos().size()==0&&currentItem.getImages().size()==0)
        {
            holder.post_img.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        holder.post_img.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                AppCompatActivity activity = (AppCompatActivity)mContext;
                if(position>=currentItem.getImages().size()) {
                    FrameLayout fl=(FrameLayout)activity.findViewById(R.id.mainFrame);
                    fl.setVisibility(View.INVISIBLE);
                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.videocontainer, VideoPlayer.newInstance(post_media[position])).addToBackStack(null).commit();

                }
                else {
                    FrameLayout fl=(FrameLayout)activity.findViewById(R.id.mainFrame);
                    fl.setVisibility(View.INVISIBLE);
                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.videocontainer, ImageViewer.newInstance(post_media[position])).addToBackStack(null).commit();
                }
            }
        });

        holder.post_img.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {

                AppCompatActivity activity = (AppCompatActivity)mContext;
                View customView = activity.getLayoutInflater().inflate(R.layout.image_container,null);

                ImageView feedimage = (ImageView)customView.findViewById(R.id.feedimage);
                ImageView video_ico = (ImageView)customView.findViewById(R.id.video_sign);

                if (position <currentItem.getImages().size()) {
                   try {
                       Glide.with(mContext).load(post_media[position]).thumbnail(Glide.with(mContext).load(R.drawable.loader_transparent)).fitCenter().into(feedimage);
                   }
                   catch (Exception e){
                       e.printStackTrace();
                   }
                    video_ico.setVisibility(View.INVISIBLE);
                    return customView;



                }
                else {
                    try {
                        Glide.with(mContext).load(post_tmb[position-currentItem.getImages().size()]).thumbnail(Glide.with(mContext).load(R.drawable.loader_transparent)).fitCenter().into(feedimage);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    video_ico.setVisibility(View.VISIBLE);
                    return customView;

                }



            }
        });


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
            post_img.setPageCount(current.getImages().size()+current.getVideos().size());
            post_img.setSlideInterval(1000000);

            username.setText(current.getUsername());
            createdAt.setText(current.getCreatedAt());
            postTXT.setText(current.getPostTXT());
            likeCount.setText(current.getLikeCount());
            commentCount.setText(current.getCommentCount());


        }



    }





}
