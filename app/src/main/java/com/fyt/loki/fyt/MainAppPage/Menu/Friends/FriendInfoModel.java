package com.fyt.loki.fyt.MainAppPage.Menu.Friends;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ergas on 11/15/2017.
 */

public class FriendInfoModel {


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
        @SerializedName("is_online")
        public boolean is_online;
        @SerializedName("notifications_status")
        public boolean notifications_status;
        @SerializedName("messages_status")
        public boolean messages_status;
        @SerializedName("updated")
        public String updated;
    }
}
