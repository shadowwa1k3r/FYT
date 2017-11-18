package com.fyt.loki.fyt;

import java.util.List;

/**
 * Created by ergas on 11/18/2017.
 */

public class PostItemType {

    private String avatar;
    private String username;
    private String createdAt;
    private String postTXT;
    private String likeCount;
    private String commentCount;
    private List<PostItemModel.Images> images;


    public PostItemType(String avatar,String username,String createdAt,String postTXT,String likeCount,String commentCount,List<PostItemModel.Images> images){
        this.avatar=avatar;
        this.username=username;
        this.createdAt=createdAt;
        this.postTXT=postTXT;
        this.likeCount=likeCount;
        this.commentCount=commentCount;
        this.images=images;
    }

    public String getPostTXT() {
        return postTXT;
    }

    public void setPostTXT(String postTXT) {
        this.postTXT = postTXT;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public List<PostItemModel.Images> getImages() {
        return images;
    }

    public void setImages(List<PostItemModel.Images> images) {
        this.images = images;
    }
}
