package com.fyt.loki.fyt;

import java.util.List;

/**
 * Created by ergas on 11/18/2017.
 */

public class PostItemType {

    private int id;
    private String avatar;
    private String username;
    private String createdAt;
    private String postTXT;
    private String likeCount;
    private String commentCount;
    private List<String> likes;
    private List<PostItemModel.Images> images;
    private List<PostItemModel.Videos> videos;
    private boolean liked=false;


    public PostItemType(int id,String avatar,String username,String createdAt,String postTXT,String likeCount,String commentCount,List<PostItemModel.Images> images,List<PostItemModel.Videos> videos,List<String> likes){
        this.id=id;
        this.avatar=avatar;
        this.username=username;
        this.createdAt=createdAt;
        this.postTXT=postTXT;
        this.likeCount=likeCount;
        this.commentCount=commentCount;
        this.images=images;
        this.videos=videos;
        this.likes=likes;
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

    public List<PostItemModel.Videos> getVideos() {
        return videos;
    }

    public void setVideos(List<PostItemModel.Videos> videos) {
        this.videos = videos;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}
