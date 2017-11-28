package com.fyt.loki.fyt;

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


    public NewsFeedItemType(String avatar, String username, String createdAt, String postTXT, String likeCount, String commentCount, List<NewsFeedModel.Images> images,List<NewsFeedModel.Videos> videos,int tID) {
        this.avatar = avatar;
        this.username = username;
        this.createdAt = createdAt;
        this.postTXT = postTXT;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.images = images;
        this.videos=videos;
        this.target_id=tID;
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
}
