package com.fyt.loki.fyt;

/**
 * Created by ergas on 11/28/2017.
 */

public class CommentType {
    private String ava,username,comment,time;
    private int cm_id;

    public CommentType(String ava,String username,String comment,String time,int cm_id){
        this.ava=ava;
        this.username=username;
        this.comment=comment;
        this.time=time;
        this.cm_id=cm_id;
    }

    public String getAva() {
        return ava;
    }

    public int getCm_id() {
        return cm_id;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }
}
