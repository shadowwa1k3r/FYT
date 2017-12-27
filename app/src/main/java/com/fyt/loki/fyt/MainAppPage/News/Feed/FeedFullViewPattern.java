package com.fyt.loki.fyt.MainAppPage.News.Feed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.MainAppPage.News.Comments.CommentPage;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ImageViewer;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.Tools.SharedPreference;
import com.fyt.loki.fyt.Tools.VideoPlayer;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FeedFullViewPattern extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private Retrofit retrofit;
    private ProfileInterface profileInt;
    private CircleImageView avatar;
    private TextView username,createdAt,postTXT,likeCount,commentCount,more,less;
    private CarouselView post_img;
    private ExpandableTextView posttxt;
    private Button comment,share,like;
    private ExpandableLayout mediasrc,textsrc;
    private SharedPreference mSharedPreference;

    private String BASE_URL;
    private NewsFeedItemType current;

    public FeedFullViewPattern() {
        // Required empty public constructor
    }


    public static FeedFullViewPattern newInstance(NewsFeedItemType feed) {
        FeedFullViewPattern fragment = new FeedFullViewPattern();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,  feed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            current = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemview=inflater.inflate(R.layout.fragment_feed_full_view_pattern, container, false);

        mSharedPreference=new SharedPreference();
        final String [] post_tmb = new String[current.getVideos().size()];
        final String [] post_media = new String[current.getVideos().size()+current.getImages().size()];







        BASE_URL=getString(R.string.BASE_URL);
        for (int i = 0; i <current.getImages().size() ; i++) {
            post_media[i] = BASE_URL+current.getImages().get(i).photo;

        }

        int t =0;
        for (int i = current.getImages().size(); i <current.getVideos().size()+current.getImages().size() ; i++) {

            post_media[i] = BASE_URL+current.getVideos().get(t).video;

            t++;

        }
        for (int i = 0; i <current.getVideos().size() ; i++) {
            post_tmb[i] = BASE_URL+current.getVideos().get(i).thumbnail;

        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInt= retrofit.create(ProfileInterface.class);

        comment=(Button)itemview.findViewById(R.id.commentBTN);
        share=(Button)itemview.findViewById(R.id.shareBTN);
        like=(Button)itemview.findViewById(R.id.likeBTN);
        avatar = (CircleImageView)itemview.findViewById(R.id.post_avaf);
        username = (TextView)itemview.findViewById(R.id.post_usernamef);
        createdAt = (TextView)itemview.findViewById(R.id.post_createdAtf);
        postTXT = (TextView) itemview.findViewById(R.id.expandable_text);
        likeCount = (TextView)itemview.findViewById(R.id.likef);
        commentCount = (TextView)itemview.findViewById(R.id.commentf);
        post_img = (CarouselView)itemview.findViewById(R.id.post_imgf);
        mediasrc=(ExpandableLayout)itemview.findViewById(R.id.mediaview);
        textsrc=(ExpandableLayout)itemview.findViewById(R.id.textsource);

        try {
            Glide.with(getContext()).load(current.getAvatar()).animate(R.anim.zoom_in).into(avatar);
        }
        catch (Exception e){
            e.printStackTrace();

        }
        if (current.getVideos().size()==0&&current.getImages().size()==0)
        {
            mediasrc.collapse();
        }
        else mediasrc.expand();
        if(current.getPostTXT().length()==0)
        {
            textsrc.collapse();
        }
        else textsrc.expand();



        post_img.setPageCount(current.getImages().size()+current.getVideos().size());
        post_img.setSlideInterval(1000000);
        if(current.getLikes().size()!=0){
            current.setLiked(false);
            for (int i = 0; i <current.getLikes().size() ; i++) {

                if (current.getLikes().get(i).equals( current.getUsername())) {
                    like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.likestar, 0, 0, 0);
                    current.setLiked(true);
                }
            }
            if(!current.isLiked()) {
                like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notlikedstar, 0, 0, 0);
            }

        }
        else {
            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notlikedstar, 0, 0, 0);
            current.setLiked(false);
        }

        username.setText(current.getUsername());
        DateTime dtIn = getDateTimeObject(current.getCreatedAt());
        createdAt.setText(jodaDateTimeToCustomString(dtIn,"HH:mm"));

        postTXT.setText(current.getPostTXT());


        likeCount.setText(current.getLikeCount());
        commentCount.setText(current.getCommentCount());

        post_img.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                if(position>=current.getImages().size()) {
                    //FrameLayout fl=(FrameLayout)getActivity().findViewById(R.id.mainFrame);
                    //fl.setVisibility(View.INVISIBLE);
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.videocontainer, VideoPlayer.newInstance(post_media[position])).addToBackStack(null).commit();

                }
                else {
                    //FrameLayout fl=(FrameLayout)activity.findViewById(R.id.mainFrame);
                    //fl.setVisibility(View.INVISIBLE);
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.videocontainer, ImageViewer.newInstance(post_media[position])).addToBackStack(null).commit();
                }
            }
        });

        post_img.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {

                View customView = getActivity().getLayoutInflater().inflate(R.layout.image_container,null);

                ImageView feedimage = (ImageView)customView.findViewById(R.id.feedimage);
                ImageView video_ico = (ImageView)customView.findViewById(R.id.video_sign);

                if (position <current.getImages().size()) {
                    try {


                        // Glide.with(mContext).load(post_media[position]).thumbnail(Glide.with(mContext).load(R.drawable.loader_transparent)).fitCenter().into(feedimage);
                        Picasso.with(getContext()).load(post_media[position]).placeholder(R.drawable.loading).fit().centerInside().into(feedimage);
                    }
                    catch (Exception e){


                        e.printStackTrace();
                    }
                    video_ico.setVisibility(View.INVISIBLE);
                    return customView;



                }
                else {
                    try {


                        Glide.with(getContext()).load(post_tmb[position - current.getImages().size()]).thumbnail(Glide.with(getContext()).load(R.drawable.loader_transparent)).fitCenter().into(feedimage);
                    }
                    catch (Exception e){
                        e.printStackTrace();

                    }
                    video_ico.setVisibility(View.VISIBLE);
                    return customView;

                }



            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.videocontainer, CommentPage.newInstance(current.getTarget_id(),mSharedPreference.getToken(getContext()),mSharedPreference.getUserName(getContext()))).addToBackStack(null).commit();
            }
        });



        likebody body=new likebody();
        body.post_id=current.getTarget_id();

        final Call<likeresponse> call=profileInt.like(" Token "+mSharedPreference.getToken(getContext()),body);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!current.isLiked()) {
                    like.setClickable(false);
                    like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.likestar, 0, 0, 0);
                    likeCount.setText(String.valueOf(Integer.parseInt(likeCount.getText().toString())+1));


                    call.clone().enqueue(new Callback<likeresponse>() {
                        @Override
                        public void onResponse(Call<likeresponse> call, Response<likeresponse> response) {
                            if (response.isSuccessful()) {
                                current.setLiked(!current.isLiked());

                                like.setClickable(true);
                            } else {
                                like.setClickable(true);
                                like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notlikedstar, 0, 0, 0);
                            }
                        }

                        @Override
                        public void onFailure(Call<likeresponse> call, Throwable t) {
                            like.setClickable(true);
                            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notlikedstar, 0, 0, 0);
                        }
                    });
                }





                else {
                    like.setClickable(false);
                    like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notlikedstar, 0, 0, 0);
                    likeCount.setText(String.valueOf(Integer.parseInt(likeCount.getText().toString())-1));

                    call.clone().enqueue(new Callback<likeresponse>() {
                        @Override
                        public void onResponse(Call<likeresponse> call, Response<likeresponse> response) {
                            if(response.isSuccessful()){
                                current.setLiked(!current.isLiked());

                                like.setClickable(true);
                            }
                            else {
                                like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.likestar, 0, 0, 0);
                                like.setClickable(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<likeresponse> call, Throwable t) {
                            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.likestar, 0, 0, 0);
                            like.setClickable(true);
                        }
                    });




                }


            }
        });




        return itemview;
    }

    public static DateTime getDateTimeObject(String dateTime) {

        DateTime dateTimeObj = null;

        dateTimeObj = ISODateTimeFormat.dateTime().parseDateTime(dateTime);

        //DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(PATTERN);
        //DateTime dateTimeObj = dateTimeFormatter.parseDateTime(dateTime);
        //Logger.d(dateTime);



        return dateTimeObj;

    }
    public static String jodaDateTimeToCustomString(DateTime dateTime, String dateTimePattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateTimePattern);
        String dateTimeString = dateTime.toString(fmt);
        return dateTimeString;
    }
}
