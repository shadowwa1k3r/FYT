package com.fyt.loki.fyt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ergas on 11/18/2017.
 */

public class PostItemModel {


    @SerializedName("id")
    public int id;
    @SerializedName("context")
    public String context;
    @SerializedName("owner")
    public Owner owner;
    @SerializedName("avatar")
    public String avatar;
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

    public static class Owner {
        @SerializedName("id")
        public int id;
        @SerializedName("username")
        public String username;
        @SerializedName("first_name")
        public String first_name;
        @SerializedName("last_name")
        public String last_name;
        @SerializedName("full_name")
        public String full_name;
        @SerializedName("email")
        public String email;
        @SerializedName("country")
        public String country;
        @SerializedName("city")
        public String city;
        @SerializedName("phone")
        public String phone;
        @SerializedName("address")
        public String address;
        @SerializedName("avatar")
        public String avatar;
    }

    public static class Images {
        @SerializedName("id")
        public int id;
        @SerializedName("photo")
        public String photo;
        @SerializedName("created")
        public String created;
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
}
