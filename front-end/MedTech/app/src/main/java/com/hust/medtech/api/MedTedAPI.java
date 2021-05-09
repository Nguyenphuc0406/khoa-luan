package com.hust.medtech.api;

import com.hust.medtech.api.request.LoginRequest;
import com.hust.medtech.api.response.LoginResponse;
import com.hust.medtech.api.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MedTedAPI {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("/getUserInfo")
    Call<UserInfoResponse> getUserInfo(@Header("Authorization") String token);
}
