package com.fyt.loki.fyt.MainAppPage.News.Feed;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ergas on 11/20/2017.
 */

public class NewsFeedItemType implements Parcelable {

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

    public List<String> getLikes() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeInt(this.target_id);
        dest.writeString(this.username);
        dest.writeString(this.createdAt);
        dest.writeString(this.postTXT);
        dest.writeString(this.likeCount);
        dest.writeString(this.commentCount);
        dest.writeList(this.images);
        dest.writeList(this.videos);
        dest.writeStringList(this.likes);
        dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
        dest.writeSparseBooleanArray(this.text_expanded);
    }

    protected NewsFeedItemType(Parcel in) {
        this.avatar = in.readString();
        this.target_id = in.readInt();
        this.username = in.readString();
        this.createdAt = in.readString();
        this.postTXT = in.readString();
        this.likeCount = in.readString();
        this.commentCount = in.readString();
        this.images = new ArrayList<NewsFeedModel.Images>();
        in.readList(this.images, NewsFeedModel.Images.class.getClassLoader());
        this.videos = new ArrayList<NewsFeedModel.Videos>();
        in.readList(this.videos, NewsFeedModel.Videos.class.getClassLoader());
        this.likes = in.createStringArrayList();
        this.liked = in.readByte() != 0;
        this.text_expanded = in.readSparseBooleanArray();
    }

    public static final Parcelable.Creator<NewsFeedItemType> CREATOR = new Parcelable.Creator<NewsFeedItemType>() {
        @Override
        public NewsFeedItemType createFromParcel(Parcel source) {
            return new NewsFeedItemType(source);
        }

        @Override
        public NewsFeedItemType[] newArray(int size) {
            return new NewsFeedItemType[size];
        }
    };
}
