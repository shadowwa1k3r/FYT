package com.fyt.loki.fyt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by ergas on 11/13/2017.
 */

public interface ProfileInterface {

    //@Headers("Authorization: Token c87dfaf88e22a588a983faf2f0f89642dcc77d27")
    @GET("info/")
    Call<ProfileModel> profileInfo(@Header("Authorization") String token);
    @GET("user/friends/")
    Call<List<FriendInfoModel>> friendInfo(@Header("Authorization") String token);
}
