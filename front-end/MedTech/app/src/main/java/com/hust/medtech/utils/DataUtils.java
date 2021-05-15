package com.hust.medtech.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hust.medtech.model.UserInfo;

public class DataUtils {

    public static UserInfo getUserInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("user","");
        return new Gson().fromJson(data,UserInfo.class);

    }
    public static String getToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);

        return sharedPreferences.getString("token","");

    }

}
