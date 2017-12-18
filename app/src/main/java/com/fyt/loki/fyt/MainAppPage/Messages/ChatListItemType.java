package com.fyt.loki.fyt.MainAppPage.Messages;

/**
 * Created by ergas on 12/16/2017.
 */

public class ChatListItemType {
    private String img,name,date,msg;
    private int unreadmsg_count;

    public ChatListItemType(String img,String usrname,String msg,String date, int unreadmsg_count){
        this.img=img;
        this.msg=msg;
        this.name=usrname;
        this.date=date;
        this.unreadmsg_count=unreadmsg_count;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getUnreadmsg_count() {
        return unreadmsg_count;
    }

    public void setUnreadmsg_count(int unreadmsg_count) {
        this.unreadmsg_count = unreadmsg_count;
    }
}
