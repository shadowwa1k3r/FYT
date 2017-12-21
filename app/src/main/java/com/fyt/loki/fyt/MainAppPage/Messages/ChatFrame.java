package com.fyt.loki.fyt.MainAppPage.Messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.DataWrapper;
import com.fyt.loki.fyt.Tools.SharedPreference;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


public class ChatFrame extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mUserName,mOpponentName,BASE_URL;
    private ArrayList<ChatFrameItemType> mMessageList;
    private ListView mListView;
    private WebSocket mWebSocket;
    private boolean mConnected=false;
    private DataWrapper msg;
    private ChatFrameMessageAdapter mMessageAdapter;
    private OkHttpClient mClient;

    private EditText outputmsg;
    private Button send;


    public ChatFrame() {
        // Required empty public constructor
    }


    public static ChatFrame newInstance(String username,String opponent_name) {
        ChatFrame fragment = new ChatFrame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        args.putString(ARG_PARAM2, opponent_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_PARAM1);
            mOpponentName=getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ChF=inflater.inflate(R.layout.fragment_chat_frame, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mListView=(ListView)ChF.findViewById(R.id.msg_list);
        send=(Button)ChF.findViewById(R.id.sendmessage);
        outputmsg=(EditText)ChF.findViewById(R.id.editmsg);
        mMessageList=new ArrayList<>();
        BASE_URL= getContext().getString(R.string.BASE_URL);


        mClient=new OkHttpClient();
        start();

       /* mMessageList.add(new ChatFrameItemType(BASE_URL+"/media/profile_images/ergash/temp_rxB76xg.png","ergash","Heeeey, who are you","18:00"));
        mMessageList.add(new ChatFrameItemType("https://findyourtraining.com/media/default/no_photo_male.png","farrukh","catch me outside, how about that?","18:30"));
        mMessageList.add(new ChatFrameItemType("https://findyourtraining.com/media/default/no_photo_male.png","farrukh","huh?","18:30"));
*/
        mMessageAdapter=new ChatFrameMessageAdapter(mMessageList,getContext());
        mListView.setAdapter(mMessageAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendMessage(outputmsg.getText().toString());
                        Toast.makeText(getContext(),String.valueOf(mConnected),Toast.LENGTH_LONG).show();
                        outputmsg.setText("");
                    }
                });
            }
        });




        return ChF;
    }
       private void start(){
           SharedPreference sharedPreference=new SharedPreference();
        Request request= new Request.Builder()
                //.url("ws://192.168.1.115:8000/chat/ZTpumabRiDUcmCan")
                //.url("ws://echo.websocket.org")
                .url("wss://findyourtraining.com/chat/1")
                .addHeader("Authorization",sharedPreference.getToken(getContext()))

                .build();
        MsgWebSocketListener listener = new MsgWebSocketListener();
        mWebSocket=mClient.newWebSocket(request,listener);
        mConnected=true;
        mClient.dispatcher().executorService().shutdown();
    }
    private void output(final String txt){
           try {
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       msg=DataWrapper.fromJson(txt);
                       if(!msg.getUser().getUsername().equals("test")) {
                           mMessageList.add(new ChatFrameItemType(BASE_URL + msg.getUser().getAvatar(), msg.getUser().getUsername(), msg.getMessage(), getCurrentTimeString()));
                           mMessageAdapter.notifyDataSetChanged();
                           mListView.setSelection(mMessageAdapter.getCount() - 1);
                       }
                   }
               });
           }
           catch (Exception e){
               e.printStackTrace();
           }


    }
    private void output2(final  String txt){
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageList.add(new ChatFrameItemType("https://findyourtraining.com/media/default/no_photo_male.png","ergash",txt,getCurrentTimeString()));
                    mMessageAdapter.notifyDataSetChanged();
                    mListView.setSelection(mMessageAdapter.getCount()-1);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void sendMessage(String text){
        if(!mConnected){
            return;
        }
        mWebSocket.send(text);
        mMessageAdapter.notifyDataSetChanged();
        mListView.setSelection(mMessageAdapter.getCount()-1);
        mMessageList.add(new ChatFrameItemType(BASE_URL+"/media/profile_images/ergash/temp_rxB76xg.png","farrukh",text,getCurrentTimeString()));


    }


    private final class MsgWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS=1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response){
//            webSocket.close(NORMAL_CLOSURE_STATUS,"Kaboom !");

        }
        @Override
        public void onMessage(WebSocket webSocket,String text){
            output(text);
        }
        @Override
        public void onMessage(WebSocket webSocket,ByteString bytes){
            output(bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket,int code, String reason){
            webSocket.close(NORMAL_CLOSURE_STATUS,null);
           // output(code+" "+reason);
        }
        @Override
        public void onFailure(WebSocket webSocket,Throwable t,Response response){
           // output(t.getMessage());
        }
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

    public static String getCurrentTimeString(){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat df= new SimpleDateFormat("HH:mm");
        String  formattedDate=df.format(c.getTime());
        return formattedDate;
    }


}
