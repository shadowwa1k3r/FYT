package com.fyt.loki.fyt;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ergas on 11/28/2017.
 */

public class CommentModel {

    @SerializedName("id")
    public int id;
    @SerializedName("text")
    public String text;
    @SerializedName("author")
    public Author author;
    @SerializedName("created")
    public String created;

    public static class Author {
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
}
