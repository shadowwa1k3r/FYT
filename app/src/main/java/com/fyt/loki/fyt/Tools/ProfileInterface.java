package com.fyt.loki.fyt.Tools;

import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendInfoModel;
import com.fyt.loki.fyt.MainAppPage.Menu.Friends.FriendPostBody;
import com.fyt.loki.fyt.MainAppPage.Menu.Settings.General_info_body;
import com.fyt.loki.fyt.MainAppPage.Menu.Settings.General_info_response;
import com.fyt.loki.fyt.MainAppPage.Menu.Settings.General_pass_body;
import com.fyt.loki.fyt.MainAppPage.Menu.Settings.General_pass_response;
import com.fyt.loki.fyt.MainAppPage.News.Comments.CommentModel;
import com.fyt.loki.fyt.MainAppPage.News.Comments.CommentReplyBody;
import com.fyt.loki.fyt.MainAppPage.News.Comments.commentBody;
import com.fyt.loki.fyt.MainAppPage.News.Comments.commentResponse;
import com.fyt.loki.fyt.MainAppPage.News.Feed.NewsFeedModel;
import com.fyt.loki.fyt.MainAppPage.News.Feed.likebody;
import com.fyt.loki.fyt.MainAppPage.News.Feed.likeresponse;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.PostItemModel;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.ProfileModel;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.createPostBody;
import com.fyt.loki.fyt.MainAppPage.ProfilePage.createPostResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ergas on 11/13/2017.
 */

public interface ProfileInterface {

    //@Headers("Authorization: Token c87dfaf88e22a588a983faf2f0f89642dcc77d27")
    @GET("user/detail/{username}/")
    Call<ProfileModel> profileInfo(@Header("Authorization") String token, @Path("username") String username);
    @GET("user/friends/")
    Call<List<FriendInfoModel>> friendInfo(@Header("Authorization") String token);
    @GET("list/post/")
    Call<List<PostItemModel>> getPosts(@Header("Authorization")String token);
    @POST("list/post/")
    Call<List<PostItemModel>> getFriendPosts(@Header("Authorization")String token, @Body FriendPostBody friendPostBody);
    @GET("list/feed/")
    Call<List<NewsFeedModel>> getNews(@Header("Authorization")String token);
    @GET("list/comment/")
    Call<List<CommentModel>> getComment(@Header("Authorization")String token, @Query("post_id") int post_id);
    @POST("add-like/")
    Call<likeresponse> like(@Header("Authorization")String token, @Body likebody likebody);
    @POST("create/comment/")
    Call<commentResponse> comment(@Header("Authorization")String token, @Body commentBody commentBody);
    @GET("list/reply")
    Call<List<CommentModel>> getreply(@Header("Authorization")String token,@Query("comment_id") int comment_id);
    @POST("create/reply/")
    Call<commentResponse> reply(@Header("Authorization")String token, @Body CommentReplyBody commentBody);
    @POST("create/post/")
    Call<createPostResponse> post(@Header("Authorization")String token, @Body createPostBody createPostBody);


    @POST("accounts/set-new-password/")
    Call<General_pass_response> change_password(@Header("Authorization")String token,@Body General_pass_body body);
    @PUT("accounts/update-profile/")
    Call<General_info_response> change_info(@Header("Authorization")String token, @Body General_info_body body);



    @Multipart
    @POST("create/post/")
    Call<createPostResponse> postimage(@Header("Authorization")String token,@Part List<MultipartBody.Part> image, @Part("name")RequestBody name,@Part ("context")RequestBody body2);



}
