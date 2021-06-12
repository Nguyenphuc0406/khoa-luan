package com.hust.medtech.api;

import com.hust.medtech.api.request.LoginRequest;
import com.hust.medtech.api.request.PaymentRequest;
import com.hust.medtech.api.request.RegisterMedRequest;
import com.hust.medtech.api.response.BaseResponse;
import com.hust.medtech.api.response.EmptyResponse;
import com.hust.medtech.api.response.GetDeptResponse;
import com.hust.medtech.api.response.GetNewsResponse;
import com.hust.medtech.api.response.GetTotalPaymentResponse;
import com.hust.medtech.api.response.LoginResponse;
import com.hust.medtech.api.response.UserInfoResponse;
import com.hust.medtech.model.Model;

import java.util.List;

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

    @GET("/getNews")
    Call<GetNewsResponse> getNews(@Header("Authorization") String token);

    @GET("/api/getDept")
    Call<GetDeptResponse> getDept(@Header("Authorization") String token);

    @POST("/api/phieu-kham")
    Call<EmptyResponse> registerMed(@Body RegisterMedRequest request,@Header("Authorization") String token);

    @GET("/api/patient/getTotalPayment")
    Call<GetTotalPaymentResponse> getTotalPayment(@Header("Authorization") String token);

    @GET("/api/patient/lich-kham")
    Call<BaseResponse<List<Model>>> getTodoList(@Header("Authorization") String token);

    @POST("/api/patient/payment")
    Call<EmptyResponse> payment(@Body PaymentRequest request, @Header("Authorization") String token);
}
