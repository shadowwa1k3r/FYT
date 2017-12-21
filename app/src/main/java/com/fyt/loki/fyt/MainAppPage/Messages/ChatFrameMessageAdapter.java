package com.fyt.loki.fyt.MainAppPage.Messages;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyt.loki.fyt.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ergas on 12/16/2017.
 */

public class ChatFrameMessageAdapter extends ArrayAdapter<ChatFrameItemType>{
    private ArrayList<ChatFrameItemType> dataSet;
    Context mContext;

    private static class ViewHolder{
        LinearLayout chat;
        LinearLayout content;
        LinearLayout date_container;
        CircleImageView ava;
        TextView msg,date;
    }

    public ChatFrameMessageAdapter(ArrayList<ChatFrameItemType> data,Context context){
        super(context,R.layout.chat_frame_item,data);
        this.dataSet=data;
        this.mContext=context;
    }

    private int lastPosition=-1;
    private boolean listsize=false;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ChatFrameItemType datamodel=getItem(position);
        ChatFrameItemType prevdatamodel=null;
        if(position>0){
            listsize=true;
             prevdatamodel=getItem(position-1);
        }
        else listsize=false;


        ViewHolder viewHolder;

        final View result;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.chat_frame_item,parent,false);
            viewHolder.ava=(CircleImageView)convertView.findViewById(R.id.chat_ava);
            viewHolder.date_container=(LinearLayout)convertView.findViewById(R.id.date_cont);
            viewHolder.content=(LinearLayout)convertView.findViewById(R.id.content);
            viewHolder.msg=(TextView)convertView.findViewById(R.id.chat_message);
            viewHolder.chat=(LinearLayout)convertView.findViewById(R.id.chat_item);
            viewHolder.date=(TextView)convertView.findViewById(R.id.chat_date);
            result=convertView;
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
            result=convertView;
        }

       /* Animation animation= AnimationUtils.loadAnimation(mContext,(position>lastPosition)?R.anim.up_from_bottom:R.anim.down_from_top);
        result.startAnimation(animation);*/
        lastPosition=position;
        viewHolder.msg.setText(datamodel.getMsg());
        viewHolder.date.setText(datamodel.getDate());
        if(listsize){
            if (datamodel.getUsername().equals(prevdatamodel.getUsername())){
                viewHolder.ava.setVisibility(View.INVISIBLE);

            }
            else {
                viewHolder.ava.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(datamodel.getAva()).into(viewHolder.ava);
            }
        }
        else {
            viewHolder.ava.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(datamodel.getAva()).into(viewHolder.ava);
        }


        if (datamodel.getUsername().equals("farrukh")){
            viewHolder.chat.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            viewHolder.date_container.setGravity(Gravity.START);
            viewHolder.content.setBackground(getContext().getDrawable(R.drawable.rounded2));

        }
        else {
            viewHolder.chat.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            viewHolder.msg.setGravity(Gravity.START);
            viewHolder.date_container.setGravity(Gravity.END);
            viewHolder.content.setBackground(getContext().getDrawable(R.drawable.rounded));
        }


        return  convertView;


    }
}
