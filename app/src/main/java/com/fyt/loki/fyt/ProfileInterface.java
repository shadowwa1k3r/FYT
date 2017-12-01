package com.fyt.loki.fyt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ergas on 11/13/2017.
 */

public interface ProfileInterface {

    //@Headers("Authorization: Token c87dfaf88e22a588a983faf2f0f89642dcc77d27")
    @GET("user/detail/{username}/")
    Call<ProfileModel> profileInfo(@Header("Authorization") String token,@Path("username") String username);
    @GET("user/friends/")
    Call<List<FriendInfoModel>> friendInfo(@Header("Authorization") String token);
    @GET("list/post/")
    Call<List<PostItemModel>> getPosts(@Header("Authorization")String token);
    @POST("list/post/")
    Call<List<PostItemModel>> getFriendPosts(@Header("Authorization")String token, @Body FriendPostBody friendPostBody);
    @GET("list/feed/")
    Call<List<NewsFeedModel>> getNews(@Header("Authorization")String token);
    @GET("list/comment/")
    Call<List<CommentModel>> getComment(@Header("Authorization")String token,@Query("post_id") int post_id);
    @POST("add-like/")
    Call<likeresponse> like(@Header("Authorization")String token,@Body likebody likebody);
    @POST("create/comment/")
    Call<commentResponse> comment(@Header("Authorization")String token, @Body commentBody commentBody);
    @GET("list/reply")
    Call<List<CommentModel>> getreply(@Header("Authorization")String token,@Query("comment_id") int comment_id);
    @POST("create/reply/")
    Call<commentResponse> reply(@Header("Authorization")String token, @Body CommentReplyBody commentBody);

}
