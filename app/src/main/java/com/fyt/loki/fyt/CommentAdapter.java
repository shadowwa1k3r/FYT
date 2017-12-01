package com.fyt.loki.fyt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ergas on 11/28/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CHolder> {

    private ArrayList<CommentType> mData;
    private Context mContext;
    private String BASE_URL,BASE_URL_API,token;
    private ProfileInterface profileInterface;

    private ArrayList<CommentType> mDataset;
    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime().withZoneUTC();



    CommentAdapter(Context context,ArrayList data,String token){
        this.token=token;
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
        final AppCompatActivity activity =(AppCompatActivity)mContext;

        BASE_URL=mContext.getString(R.string.BASE_URL);
        Glide.with(mContext).load(BASE_URL+currentItem.getAva()).animate(R.anim.zoom_in).into(holder.avatar);

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.replies.isExpanded())
                    holder.replies.collapse();
                else {
                    holder.replies.expand();

                    BASE_URL_API =BASE_URL+"/api/";

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    profileInterface = retrofit.create(ProfileInterface.class);

                    holder.mRecyclerView.setHasFixedSize(true);

                    holder.mLayoutManager=new LinearLayoutManager(activity);
                    holder.mRecyclerView.setLayoutManager(holder.mLayoutManager);

                    mDataset=new ArrayList<CommentType>();

                    Call<List<CommentModel>> getreplies=profileInterface.getreply(" Token "+token,currentItem.getCm_id());
                    getreplies.enqueue(new Callback<List<CommentModel>>() {
                        @Override
                        public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                            if(response.isSuccessful())
                            {
                                for (int i = 0; i <response.body().size() ; i++) {

                                    mDataset.add(new CommentType(response.body().get(i).author.avatar,response.body().get(i).author.username,response.body().get(i).text,response.body().get(i).created,response.body().get(i).id));


                                }

                                holder.mCommentAdapter = new CommentAdapter(activity,mDataset,token);
                                holder.mCommentAdapter.notifyDataSetChanged();
                                holder.mRecyclerView.setAdapter(holder.mCommentAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<CommentModel>> call, Throwable t) {

                        }
                    });


                    final CommentReplyBody body=new CommentReplyBody();
                    holder.repSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            body.text=holder.repEdit.getText().toString();
                            body.comment=currentItem.getCm_id();
                            Call<commentResponse> reply=profileInterface.reply(" Token "+token,body);
                            reply.enqueue(new Callback<commentResponse>() {
                                @Override
                                public void onResponse(Call<commentResponse> call, Response<commentResponse> response) {
                                    if(response.isSuccessful())
                                    {
                                        Random random=new Random();
                                        DateTime dt=new DateTime(DateTimeZone.UTC);
                                        mDataset.add(new CommentType(currentItem.getAva(),currentItem.getUsername(),body.text,jodaDateTimeToIsoString(dt),random.nextInt(1000000)));
                                        holder.mCommentAdapter.notifyDataSetChanged();
                                        holder.repEdit.setText("");
                                        holder.mLayoutManager.scrollToPosition(mDataset.size()-1);
                                        InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);

                                        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                                    }
                                }

                                @Override
                                public void onFailure(Call<commentResponse> call, Throwable t) {

                                }
                            });
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

    class CHolder extends RecyclerView.ViewHolder{
        private CircleImageView avatar;
        private ExpandableLayout replies;
        private RecyclerView mRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;
        private CommentAdapter mCommentAdapter;
        private EditText repEdit;
        private ImageButton repSend;



        private TextView username,comment,date,reply;

        CHolder(final View itemview){
            super(itemview);
            repSend=(ImageButton)itemview.findViewById(R.id.sendReply);
            repEdit=(EditText)itemview.findViewById(R.id.replyEditText);
            mRecyclerView=(RecyclerView)itemview.findViewById(R.id.CommentReplyRV);
            replies=(ExpandableLayout)itemview.findViewById(R.id.repliesLay);
            reply=(TextView) itemview.findViewById(R.id.CommentREPLY);
            avatar=(CircleImageView)itemview.findViewById(R.id.commentava);
            username=(TextView)itemview.findViewById(R.id.commentUser);
            comment=(TextView)itemview.findViewById(R.id.commentTXT);
            date=(TextView)itemview.findViewById(R.id.commentTIME);

        }

        void bindTo(CommentType current){
           // Toast.makeText(mContext,current.getComment(),Toast.LENGTH_LONG).show();
            username.setText(current.getUsername());
            comment.setText(current.getComment());
            DateTime dtIn = getDateTimeObject(current.getTime());

            date.setText(jodaDateTimeToCustomString(dtIn,"HH:mm"));
        }
    }
    public static DateTime getDateTimeObject(String dateTime) {
        //DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(PATTERN);
        //DateTime dateTimeObj = dateTimeFormatter.parseDateTime(dateTime);
        //Logger.d(dateTime);
        DateTime dateTimeObj = null;

        dateTimeObj = ISODateTimeFormat.dateTime().parseDateTime(dateTime);



        return dateTimeObj;

    }
    public static String jodaDateTimeToCustomString(DateTime dateTime, String dateTimePattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateTimePattern);
        String dateTimeString = dateTime.toString(fmt);
        return dateTimeString;
    }
    public static String jodaDateTimeToIsoString(DateTime dateTime) {
        String dateTimeString = ISO_DATE_TIME_FORMATTER.print(dateTime);
        return dateTimeString;
    }
}
