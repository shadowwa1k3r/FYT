package com.fyt.loki.fyt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ergas on 11/18/2017.
 */

public class NewsPostsAdapter extends RecyclerView.Adapter<NewsPostsAdapter.NFVHolder> {

    private ArrayList<NewsFeedItemType> mData;
    private Context mContext;
    private String BASE_URL;
    private String token,username;



    NewsPostsAdapter(Context context, ArrayList data,String token,String username){
        this.mData=data;
        this.username=username;
        this.token=token;
        this.mContext=context;
    }

    @Override
    public NewsPostsAdapter.NFVHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new NFVHolder(LayoutInflater.from(mContext).inflate(R.layout.news_feed_item,parent,false));
    }
    @Override
    public void onBindViewHolder(final NFVHolder holder,final int position){
        final  NewsFeedItemType currentItem = mData.get(position);

        final LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        BASE_URL=mContext.getString(R.string.BASE_URL);
        Glide.with(mContext).load(BASE_URL+currentItem.getAvatar()).animate(R.anim.zoom_in).into(holder.avatar);
        final String [] post_tmb = new String[currentItem.getVideos().size()];
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




      /*  holder.post_img.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                if (position <currentItem.getImages().size()) {
                    Glide.with(mContext).load(post_media[position]).thumbnail(Glide.with(mContext).load(R.drawable.loading)).into(imageView);


                }
                else {
                    Glide.with(mContext).load(post_tmb[position-currentItem.getImages().size()]).thumbnail(Glide.with(mContext).load(R.drawable.loading)).into(imageView);

                }

            }
        });*/

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
                    Glide.with(mContext).load(post_media[position]).thumbnail(Glide.with(mContext).load(R.drawable.loader_transparent)).fitCenter().into(feedimage);
                    video_ico.setVisibility(View.INVISIBLE);
                    return customView;



                }
                else {
                    Glide.with(mContext).load(post_tmb[position-currentItem.getImages().size()]).thumbnail(Glide.with(mContext).load(R.drawable.loader_transparent)).fitCenter().into(feedimage);
                    video_ico.setVisibility(View.VISIBLE);
                    return customView;

                }



            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity =(AppCompatActivity)mContext;

                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.videocontainer,CommentPage.newInstance(currentItem.getTarget_id(),token,username)).addToBackStack(null).commit();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.BASE_URL)+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ProfileInterface profileInt= retrofit.create(ProfileInterface.class);
        likebody body=new likebody();
        body.post_id=currentItem.getTarget_id();

        final Call<likeresponse> call=profileInt.like(" Token "+token,body);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!currentItem.isLiked()) {
                    holder.like.setClickable(false);
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon_full, 0, 0, 0);
                    holder.likeCount.setText(String.valueOf(Integer.parseInt(holder.likeCount.getText().toString())+1));


                    call.clone().enqueue(new Callback<likeresponse>() {
                        @Override
                        public void onResponse(Call<likeresponse> call, Response<likeresponse> response) {
                            if (response.isSuccessful()) {
                                currentItem.setLiked(!currentItem.isLiked());

                                holder.like.setClickable(true);
                            } else {
                                holder.like.setClickable(true);
                                holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon, 0, 0, 0);
                            }
                        }

                        @Override
                        public void onFailure(Call<likeresponse> call, Throwable t) {
                            holder.like.setClickable(true);
                            holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon, 0, 0, 0);
                        }
                    });
                }





                else {
                    holder.like.setClickable(false);
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon, 0, 0, 0);
                    holder.likeCount.setText(String.valueOf(Integer.parseInt(holder.likeCount.getText().toString())-1));

                    call.clone().enqueue(new Callback<likeresponse>() {
                        @Override
                        public void onResponse(Call<likeresponse> call, Response<likeresponse> response) {
                            if(response.isSuccessful()){
                                currentItem.setLiked(!currentItem.isLiked());

                                holder.like.setClickable(true);
                            }
                            else {
                                holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon_full, 0, 0, 0);
                                holder.like.setClickable(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<likeresponse> call, Throwable t) {
                            holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon_full, 0, 0, 0);
                            holder.like.setClickable(true);
                        }
                    });




                }


            }
        });



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
        private Button comment,share,like;


        NFVHolder(final View itemview){
            super(itemview);
            comment=(Button)itemview.findViewById(R.id.commentBTN);
            share=(Button)itemview.findViewById(R.id.shareBTN);
            like=(Button)itemview.findViewById(R.id.likeBTN);
            avatar = (CircleImageView)itemview.findViewById(R.id.post_avaf);
            username = (TextView)itemview.findViewById(R.id.post_usernamef);
            createdAt = (TextView)itemview.findViewById(R.id.post_createdAtf);
            postTXT = (TextView)itemview.findViewById(R.id.post_txtf);
            likeCount = (TextView)itemview.findViewById(R.id.likef);
            commentCount = (TextView)itemview.findViewById(R.id.commentf);
            post_img = (CarouselView)itemview.findViewById(R.id.post_imgf);
        }

        void bindTo(NewsFeedItemType current){
            post_img.setPageCount(current.getImages().size()+current.getVideos().size());
            post_img.setSlideInterval(1000000);

            if(current.getLikes().size()!=0){
                current.setLiked(false);
            for (int i = 0; i <current.getLikes().size() ; i++) {

                if (current.getLikes().get(i).equals( current.getUsername())) {
                    like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon_full, 0, 0, 0);
                    current.setLiked(true);


                }
            }
                if(!current.isLiked()) {
                    like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon, 0, 0, 0);


                }

            }
            else {
                like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon, 0, 0, 0);
                current.setLiked(false);



            }

            username.setText(current.getUsername());


            DateTime dtIn = getDateTimeObject(current.getCreatedAt());




                createdAt.setText(jodaDateTimeToCustomString(dtIn,"HH:mm"));









            postTXT.setText(current.getPostTXT());
            likeCount.setText(current.getLikeCount());
            commentCount.setText(current.getCommentCount());


        }





    }
    public static DateTime getDateTimeObject(String dateTime) {
        //DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(PATTERN);
        //DateTime dateTimeObj = dateTimeFormatter.parseDateTime(dateTime);
        //Logger.d(dateTime);
        DateTime dateTimeObj = null;

        dateTimeObj = ISODateTimeFormat.dateTime().parseDateTime(dateTime);





        return dateTimeObj;

    }public static String jodaDateTimeToCustomString(DateTime dateTime, String dateTimePattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateTimePattern);
        String dateTimeString = dateTime.toString(fmt);
        return dateTimeString;
    }







}
