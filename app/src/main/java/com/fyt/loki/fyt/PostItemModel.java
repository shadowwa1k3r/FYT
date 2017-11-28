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
    public String owner;
    @SerializedName("avatar")
    public String avatar;
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

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
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

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
