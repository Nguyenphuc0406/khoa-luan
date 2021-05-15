package com.hust.medtech.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFetchData {
    private static Retrofit retrofit;

    public static ServiceApi getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.106:8080").
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ServiceApi.class);
    }

}
