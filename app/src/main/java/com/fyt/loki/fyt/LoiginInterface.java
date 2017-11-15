package com.fyt.loki.fyt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ergas on 11/11/2017.
 */

public interface LoiginInterface {
    @POST("auth/login/")
    Call<RegistrationResponse> registerUser(@Body RegistrationBody registrationBody);
}
