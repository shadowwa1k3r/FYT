package com.fyt.loki.fyt.MainAppPage.News.Feed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ergas on 11/20/2017.
 */

public class NewsFeedModel {


    @SerializedName("id")
    public int id;
    @SerializedName("verb")
    public String verb;
    @SerializedName("target_id")
    public int target_id;
    @SerializedName("created")
    public String created;
    @SerializedName("user")
    public User user;
    @SerializedName("target")
    public Target target;
    @SerializedName("target_ct")
    public int target_ct;

    public static class Profile {
        @SerializedName("id")
        public int id;
        @SerializedName("birth_date")
        public String birth_date;
        @SerializedName("gender")
        public int gender;
        @SerializedName("phone")
        public String phone;
        @SerializedName("country")
        public String country;
        @SerializedName("city")
        public String city;
        @SerializedName("address")
        public String address;
        @SerializedName("friends_count")
        public int friends_count;
        @SerializedName("channel")
        public String channel;
        @SerializedName("avatar")
        public String avatar;
        @SerializedName("location")
        public String location;
        @SerializedName("language")
        public String language;
        @SerializedName("notifications_status")
        public boolean notifications_status;
        @SerializedName("messages_status")
        public boolean messages_status;
        @SerializedName("updated")
        public String updated;
    }

    public static class User {
        @SerializedName("id")
        public int id;
        @SerializedName("username")
        public String username;
        @SerializedName("first_name")
        public String first_name;
        @SerializedName("last_name")
        public String last_name;
        @SerializedName("email")
        public String email;
        @SerializedName("profile")
        public Profile profile;
    }



    public static class Owner {
        @SerializedName("id")
        public int id;
        @SerializedName("username")
        public String username;
        @SerializedName("first_name")
        public String first_name;
        @SerializedName("last_name")
        public String last_name;
        @SerializedName("email")
        public String email;
        @SerializedName("profile")
        public Profile profile;
    }

    public static class Images {
        @SerializedName("id")
        public int id;
        @SerializedName("photo")
        public String photo;
        @SerializedName("created")
        public String created;
        @SerializedName("width")
        public String width;
        @SerializedName("height")
        public String height;
    }

    public static class Videos {
        @SerializedName("id")
        public int id;
        @SerializedName("video")
        public String video;
        @SerializedName("thumbnail")
        public String thumbnail;
        @SerializedName("created")
        public String created;
    }

    public static class Target {
        @SerializedName("id")
        public int id;
        @SerializedName("context")
        public String context;
        @SerializedName("owner")
        public Owner owner;
        @SerializedName("images")
        public List<Images> images;
        @SerializedName("videos")
        public List<Videos> videos;
        @SerializedName("likes")
        public List<String> likes;
        @SerializedName("likes_count")
        public String likes_count;
        @SerializedName("comments")
        public String comments;
        @SerializedName("created")
        public String created;
    }
}
