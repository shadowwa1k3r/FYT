package com.fyt.loki.fyt.MainAppPage.ProfilePage;

import com.fyt.loki.fyt.MainAppPage.News.Feed.NewsFeedModel;

import java.util.List;

/**
 * Created by ergas on 12/2/2017.
 */

public class createPostResponse {


    public int id;

    public String context;

    public Owner owner;

    public String avatar;

    public List<NewsFeedModel.Images> images;

    public List<NewsFeedModel.Videos> videos;

    public List<String> likes;

    public String likes_count;

    public String comments;

    public String created;

    public static class Owner {

        public int id;

        public String username;

        public String first_name;

        public String last_name;

        public String full_name;

        public String email;

        public String country;

        public String city;

        public String phone;

        public String address;

        public String avatar;
    }




}
