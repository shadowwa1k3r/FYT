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
    @SerializedName("email")
    public String email;
    @SerializedName("avatar")
    public String avatar;
    @SerializedName("birth_date")
    public String birth_date;
    @SerializedName("phone")
    public String phone;
    @SerializedName("address")
    public String address;
    @SerializedName("country")
    public String country;
    @SerializedName("city")
    public String city;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIs_online() {
        return is_online;
    }

    public void setIs_online(Boolean is_online) {
        this.is_online = is_online;
    }
}
