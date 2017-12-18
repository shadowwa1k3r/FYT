package com.fyt.loki.fyt.MainAppPage.Messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fyt.loki.fyt.Tools.CommonMethods;
import com.fyt.loki.fyt.R;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatPage extends Fragment implements View.OnClickListener {
    private EditText msg_edittext;
    private String user1 = "Loki", user2 = "Thor";
    private Random random;
    public static ArrayList<ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;


    public ChatPage() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ChatPage newInstance() {
        ChatPage fragment = new ChatPage();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v_chat = inflater.inflate(R.layout.fragment_chat_page, container, false);
        // Inflate the layout for this fragment

        random = new Random();
        msg_edittext = (EditText)v_chat.findViewById(R.id.messageEditText);
        msgListView = (ListView)v_chat.findViewById(R.id.msgListView);
        ImageButton sendButton =(ImageButton)v_chat.findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);

        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<ChatMessage>();
        chatAdapter = new ChatAdapter(getActivity(),chatlist);
        msgListView.setAdapter(chatAdapter);
        receiveTextMessage(v_chat,"Hey Buddy");
        receiveTextMessage(v_chat,"wassup,how r u doin");
        receiveTextMessage(v_chat, "bla bla");
        receiveTextMessage(v_chat,"alb alb");
        receiveTextMessage(v_chat,"%^#$^%87234");


        return v_chat;
    }

    private void sendTextMessage(View v){
        String message = msg_edittext.getEditableText().toString();
        if(!message.equalsIgnoreCase("")){
            final ChatMessage chatMessage = new ChatMessage(user1,user2,message,""+random.nextInt(1000),true);
            chatMessage.setMsgID();
            chatMessage.body=message;
            chatMessage.Date= CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
        }
    }
    private void receiveTextMessage(View v,String message){

        if(!message.equalsIgnoreCase("")){
            final ChatMessage chatMessage = new ChatMessage(user2,user1,message,""+random.nextInt(1000),false);
            chatMessage.setMsgID();
            chatMessage.body=message;
            chatMessage.Date=CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.sendMessageButton:
                sendTextMessage(v);
        }
    }


}
