package com.fyt.loki.fyt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ergas on 11/24/2017.
 */

public class ChatAdapter extends BaseAdapter {

    private static LayoutInflater sInflater = null;
    ArrayList<ChatMessage> chatMessageList;

    public ChatAdapter(Activity activity, ArrayList<ChatMessage> list){
        chatMessageList=list;
        sInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount(){
        return chatMessageList.size();
    }
    @Override
    public Object getItem(int position){
        return position;
    }
    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ChatMessage message = (ChatMessage)chatMessageList.get(position);
        View vi = convertView;
        if(convertView==null)
            vi=sInflater.inflate(R.layout.chatbuble,null);

        TextView msg = (TextView)vi.findViewById(R.id.message_text);
        msg.setText(message.body);
        LinearLayout layout = (LinearLayout)vi.findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout)vi.findViewById(R.id.bubble_layout_parent);
        LinearLayout msg_layout = (LinearLayout)vi.findViewById(R.id.msg_layout);
       // CircleImageView imgava=(CircleImageView)vi.findViewById(R.id.message_avaf);



        if (message.isMine){
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
            msg_layout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        }
        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
            msg_layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }
        msg.setTextColor(Color.BLACK);
        return vi;
    }
    public void add(ChatMessage object) {
        chatMessageList.add(object);
    }


}
