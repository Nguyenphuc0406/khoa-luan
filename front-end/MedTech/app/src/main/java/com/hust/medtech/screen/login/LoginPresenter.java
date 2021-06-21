package com.hust.medtech.screen.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.request.LoginRequest;
import com.hust.medtech.api.response.LoginResponse;
import com.hust.medtech.api.response.UserInfoResponse;
import com.hust.medtech.base.BaseView;
import com.hust.medtech.screen.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private Context mContext;
    private BaseView view;

    public ObservableField<String> userName;
    public ObservableField<String> pass;

    public LoginPresenter(Context mContext,BaseView view) {
        this.mContext = mContext;
        this.view = view;
        initData();
    }

    private void initData() {
        userName = new ObservableField<>("hoanganh");
        pass = new ObservableField<>("123456");
    }

    public void login() {
        if (TextUtils.isEmpty(userName.get())) {
            Toast.makeText(mContext, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass.get())) {
            Toast.makeText(mContext, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        view.showLoading();
        LoginRequest request = new LoginRequest();
        request.setPassword(pass.get().trim());
        request.setUsername(userName.get().trim());
        MedTedInstance.getInstance().login(request)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() == 200) {
                            SharedPreferences sharedPreferences = mContext.getSharedPreferences("test", Context.MODE_PRIVATE);
                            String token = "Bearer " + response.body().getData();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            // Save.
                            editor.apply();
                            editor.commit();

                            getUserInfo(token);
                        } else {
                            onFailure(null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(mContext, "Login fail", Toast.LENGTH_SHORT).show();
                        view.hideLoading();
                    }
                });
    }

    private void getUserInfo(String token) {
        MedTedInstance.getInstance().getUserInfo(token)
                .enqueue(new Callback<UserInfoResponse>() {
                    @Override
                    public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                        if (response.code() == 200) {

                            SharedPreferences sharedPreferences = mContext.getSharedPreferences("test", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user", new Gson().toJson(response.body().getData()));
                            // Save.
                            editor.apply();
                            editor.commit();
                            view.hideLoading();
                            mContext.startActivity(new Intent(mContext, HomeActivity.class));
                        } else {
                            onFailure(null, null);
                        }
                    }
                    @Override
                    public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                        Toast.makeText(mContext, "Login fail", Toast.LENGTH_SHORT).show();
                        view.hideLoading();
                    }
                });
    }
}
