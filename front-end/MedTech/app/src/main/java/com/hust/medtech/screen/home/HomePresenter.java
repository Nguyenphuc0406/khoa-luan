package com.hust.medtech.screen.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.response.GetNewsResponse;
import com.hust.medtech.api.response.UserInfoResponse;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.CallBack;
import com.hust.medtech.model.Notify;
import com.hust.medtech.model.UserInfo;
import com.hust.medtech.screen.notify.NotifyActivity;
import com.hust.medtech.screen.requestmed.RequestMedTechActivity;
import com.hust.medtech.screen.requestmed.fragment.RequestStep1Fragment;
import com.hust.medtech.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private Context mContext;
    public ObservableInt type;
    public ObservableField<String> username;
    public ObservableField<BaseRecyclerview<Notify>> mAdapter;
private  List<Notify> strings;



    public HomePresenter(Context mContext) {
        this.mContext = mContext;
        initData();
    }
    private void initData(){
        type = new ObservableInt();
        strings = new ArrayList<>();
        username = new ObservableField(DataUtils.getUserInfo(mContext).getFullName());
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(mContext,strings, R.layout.item_notify));
        mAdapter.get().setCallBack(new CallBack<Notify>() {
            @Override
            public void onClick(Notify item) {
                Intent intent = new Intent(mContext,NotifyActivity.class);
                intent.putExtra("url",item.getSrcUrl());
                mContext.startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("test", Context.MODE_PRIVATE);



        getNew(   sharedPreferences.getString("token",""));
    }

    public void onChangeMenu(int type){
        this.type.set(type);
        this.type.notifyChange();
        if(type ==1){
            mContext.startActivity(new Intent(mContext, RequestMedTechActivity.class));

        }
    }
    private void getNew(String token){
        MedTedInstance.getInstance().getNews(token)
                .enqueue(new Callback<GetNewsResponse>() {
                    @Override
                    public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
                        if(response.code() == 200){
                            strings.addAll(response.body().getData());
                            mAdapter.notifyChange();
                        }else {
                            onFailure(null,null);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsResponse> call, Throwable t) {
                        Toast.makeText(mContext, "Login fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
