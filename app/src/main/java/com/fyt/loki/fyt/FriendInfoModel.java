package com.fyt.loki.fyt;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ergas on 11/15/2017.
 */

public class FriendInfoModel {

    @SerializedName("id")
    public String id;
    @SerializedName("username")
    public String username;
    @SerializedName("first_name")
    public String first_name;
    @SerializedName("last_name")
    public String last_name;
    @SerializedName("full_name")
    public String full_name;
    @SerializedName("avatar")
    public String avatar;
    @SerializedName("is_online")
    public Boolean is_online;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIs_online() {
        return is_online;
    }

    public void setIs_online(Boolean is_online) {
        this.is_online = is_online;
    }
}
