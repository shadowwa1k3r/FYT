package com.fyt.loki.fyt.MainAppPage.News.Feed;

import android.util.SparseBooleanArray;

import java.util.List;

/**
 * Created by ergas on 11/20/2017.
 */

public class NewsFeedItemType {

    private String avatar;
    private int target_id;
    private String username;
    private String createdAt;
    private String postTXT;
    private String likeCount;
    private String commentCount;
    private List<NewsFeedModel.Images> images;
    private List<NewsFeedModel.Videos> videos;
    private List<String> likes;
    private boolean liked=false;
    SparseBooleanArray text_expanded=new SparseBooleanArray(1);


    public NewsFeedItemType(String avatar, String username, String createdAt, String postTXT, String likeCount, String commentCount, List<NewsFeedModel.Images> images,List<NewsFeedModel.Videos> videos,int tID,List<String> likes) {
        this.likes=likes;
        this.avatar = avatar;
        this.username = username;
        this.createdAt = createdAt;
        this.postTXT = postTXT;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.images = images;
        this.videos=videos;
        this.target_id=tID;
        text_expanded.put(0,true);
    }



    public List<NewsFeedModel.Videos> getVideos() {
        return videos;
    }

    public void setVideos(List<NewsFeedModel.Videos> videos) {
        this.videos = videos;
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

    public String getPostTXT() {
        return postTXT;
    }

    public void setPostTXT(String postTXT) {
        this.postTXT = postTXT;
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

    public List<NewsFeedModel.Images> getImages() {
        return images;
    }

    public void setImages(List<NewsFeedModel.Images> images) {
        this.images = images;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public List getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
