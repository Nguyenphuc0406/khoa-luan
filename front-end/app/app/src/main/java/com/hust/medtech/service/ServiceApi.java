package com.hust.medtech.service;

import com.hust.medtech.model.DanhMuc;
import com.hust.medtech.model.OKItem;
import com.hust.medtech.request.YeuCauKhamBenhRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {
    @GET("/khambenh/danhsachkhambenh")
    Call<List<DanhMuc>> getDanhSachDanhMuc();

    @POST("/khambenh/yeucaukhambenh")
    Call<OKItem> getDanhSachDanhMuc(@Body YeuCauKhamBenhRequest request);
}
