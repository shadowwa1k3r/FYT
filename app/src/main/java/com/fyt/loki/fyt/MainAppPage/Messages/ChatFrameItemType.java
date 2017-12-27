package com.fyt.loki.fyt.MainAppPage.Messages;

/**
 * Created by ergas on 12/16/2017.
 */

public class ChatFrameItemType {
    private String ava,me,username,date,msg;

    public ChatFrameItemType(String ava,String me,String username,String msg,String date){
        this.ava=ava;
        this.me=me;
        this.username=username;
        this.msg=msg;
        this.date=date;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {

        this.ava = ava;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
