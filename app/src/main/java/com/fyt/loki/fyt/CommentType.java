package com.fyt.loki.fyt;

/**
 * Created by ergas on 11/28/2017.
 */

public class CommentType {
    private String ava,username,comment,time;

    public CommentType(String ava,String username,String comment,String time){
        this.ava=ava;
        this.username=username;
        this.comment=comment;
        this.time=time;
    }

    public String getAva() {
        return ava;
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
