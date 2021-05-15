package com.hust.medtech.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedTedInstance {

    static final String BASE_URL = "http://192.168.1.105:8080";
    private static volatile MedTedAPI instance;

    public static MedTedAPI getInstance() {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            instance = retrofit.create(MedTedAPI.class);
        }
        return instance;
    }
}
