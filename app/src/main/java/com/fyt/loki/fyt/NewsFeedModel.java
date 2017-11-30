package com.fyt.loki.fyt;

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

    public static class User {
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

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getFull_name() {
            return full_name;
        }

        public String getEmail() {
            return email;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getAvatar() {
            return avatar;
        }
    }

    public static class Images {
        @SerializedName("id")
        public int id;
        @SerializedName("photo")
        public String photo;
        @SerializedName("created")
        public String created;

        public int getId() {
            return id;
        }

        public String getPhoto() {
            return photo;
        }

        public String getCreated() {
            return created;
        }
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

        public int getId() {
            return id;
        }

        public String getVideo() {
            return video;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getCreated() {
            return created;
        }
    }

    public static class Target {
        @SerializedName("id")
        public int id;
        @SerializedName("context")
        public String context;
        @SerializedName("owner")
        public String owner;
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

        public int getId() {
            return id;
        }

        public String getContext() {
            return context;
        }

        public String getOwner() {
            return owner;
        }

        public String getAvatar() {
            return avatar;
        }

        public List<Images> getImages() {
            return images;
        }

        public List<Videos> getVideos() {
            return videos;
        }

        public List<String> getLikes() {
            return likes;
        }

        public String getLikes_count() {
            return likes_count;
        }

        public String getComments() {
            return comments;
        }

        public String getCreated() {
            return created;
        }
    }

    public int getId() {
        return id;
    }

    public String getVerb() {
        return verb;
    }

    public int getTarget_id() {
        return target_id;
    }

    public String getCreated() {
        return created;
    }

    public User getUser() {
        return user;
    }

    public Target getTarget() {
        return target;
    }

    public int getTarget_ct() {
        return target_ct;
    }

}
