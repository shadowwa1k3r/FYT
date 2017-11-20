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
    public int user;
    @SerializedName("target")
    public Target target;
    @SerializedName("target_ct")
    public int target_ct;

    public static class Images {
        @SerializedName("id")
        public int id;
        @SerializedName("photo")
        public String photo;
        @SerializedName("created")
        public String created;
    }

    public static class Videos {
    }

    public static class Target {
        @SerializedName("id")
        public int id;
        @SerializedName("context")
        public String context;
        @SerializedName("owner")
        public int owner;
        @SerializedName("images")
        public List<Images> images;
        @SerializedName("videos")
        public List<Videos> videos;
        @SerializedName("likes")
        public String likes;
        @SerializedName("comments")
        public String comments;
        @SerializedName("created")
        public String created;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
            this.owner = owner;
        }

        public List<Images> getImages() {
            return images;
        }

        public void setImages(List<Images> images) {
            this.images = images;
        }

        public List<Videos> getVideos() {
            return videos;
        }

        public void setVideos(List<Videos> videos) {
            this.videos = videos;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public int getTarget_ct() {
        return target_ct;
    }

    public void setTarget_ct(int target_ct) {
        this.target_ct = target_ct;
    }
}
