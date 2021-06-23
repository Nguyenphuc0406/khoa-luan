package com.hust.medtech.screen.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.request.LoginRequest;
import com.hust.medtech.api.response.BaseResponse;
import com.hust.medtech.api.response.EmptyResponse;
import com.hust.medtech.api.response.GetNewsResponse;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.BaseView;
import com.hust.medtech.base.CallBack;
import com.hust.medtech.model.Model;
import com.hust.medtech.model.Notify;
import com.hust.medtech.model.Role;
import com.hust.medtech.model.UserInfo;
import com.hust.medtech.screen.PaymentActivity;
import com.hust.medtech.screen.doctormed.DoctorMedActivity;
import com.hust.medtech.screen.notify.NotifyActivity;
import com.hust.medtech.screen.requestmed.RequestMedTechActivity;
import com.hust.medtech.screen.todo.ChiTietKhamActivity;
import com.hust.medtech.screen.todo.TodoCustomerActivity;
import com.hust.medtech.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;

public class HomePresenter {
    private Context mContext;
    public ObservableInt type;
    private BaseView baseView;
    public ObservableBoolean isDoctor;
    public ObservableField<String> username;
    public ObservableInt tab;
    public ObservableField<BaseRecyclerview<Notify>> mAdapter;
    public ObservableField<BaseRecyclerview<Model>> mAdapterNotify;
    public ObservableField<BaseRecyclerview<Model>> mAdapterTrans;
    private List<Model> notifys;
    private List<Model> listTrans;


    private List<Notify> strings;


    public HomePresenter(Context mContext,BaseView baseView) {
        this.mContext = mContext;
        this.baseView = baseView;
        tab = new ObservableInt(1);
        initData();
    }

    private void initData() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        registerDeviceToken(token);
                        // Log and toast

                        Log.d("TOKEN : ", token);

                    }
                });


        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);

        type = new ObservableInt();
        strings = new ArrayList<>();
        notifys = new ArrayList<>();
        listTrans = new ArrayList<>();
        isDoctor = new ObservableBoolean();
        UserInfo userInfo = DataUtils.getUserInfo(mContext);
        if (Role.ROLE_ADMIN.equals(userInfo.getRole())) {
            isDoctor.set(true);
            username = new ObservableField("Bác sĩ: " + DataUtils.getUserInfo(mContext).getFullName());
        } else {
            username = new ObservableField("Bệnh nhân: " + DataUtils.getUserInfo(mContext).getFullName());

        }
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(mContext, strings, R.layout.item_notify));
        mAdapterNotify = new ObservableField<>(new BaseRecyclerview<>(mContext, notifys, R.layout.item_notify_app));
        mAdapterTrans = new ObservableField<>(new BaseRecyclerview<>(mContext, listTrans, R.layout.item_pay));
        mAdapter.get().setCallBack(new CallBack<Notify>() {
            @Override
            public void onClick(Notify item) {
                Intent intent = new Intent(mContext, NotifyActivity.class);
                intent.putExtra("url", item.getSrcUrl());
                mContext.startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("test", Context.MODE_PRIVATE);


        getNew(sharedPreferences.getString("token", ""));
    }

    public void onChangeMenu(int type) {
        this.type.set(type);
        this.type.notifyChange();
        if (type == 1) {
            mContext.startActivity(new Intent(mContext, RequestMedTechActivity.class));

        } else if (type == 2) {
            mContext.startActivity(new Intent(mContext, PaymentActivity.class));
//           requestPayment();

        } else if (type == 3) {
            mContext.startActivity(new Intent(mContext, TodoCustomerActivity.class));

        } else if (type == 4) {
            mContext.startActivity(new Intent(mContext, DoctorMedActivity.class));

        }
    }

    private void getNew(String token) {
        MedTedInstance.getInstance().getNews(token)
                .enqueue(new Callback<GetNewsResponse>() {
                    @Override
                    public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
                        if (response.code() == 200) {
                            strings.addAll(response.body().getData());
                            mAdapter.notifyChange();
                        } else {
                            onFailure(null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsResponse> call, Throwable t) {
                        Toast.makeText(mContext, "Login fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registerDeviceToken(String token) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("test", Context.MODE_PRIVATE);

        String deviceToken = sharedPreferences.getString("deviceToken", "");
        if (!token.equals(deviceToken)) {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setDeviceToken(token);

            MedTedInstance.getInstance().device(loginRequest, DataUtils.getToken(mContext))
                    .enqueue(new Callback<EmptyResponse>() {
                        @Override
                        public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {
                            if (response.code() == 200) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("deviceToken", token);
                                // Save.
                                editor.apply();
                                editor.commit();

                            } else {
                                onFailure(null, null);
                            }
                        }

                        @Override
                        public void onFailure(Call<EmptyResponse> call, Throwable t) {

                        }
                    });
        }
    }

    public void loadNotify(){
        notifys.clear();
        mAdapterNotify.notifyChange();
           baseView.showLoading();
            MedTedInstance.getInstance().notify(DataUtils.getToken(mContext))
                    .enqueue(new Callback<BaseResponse<List<Model>>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<List<Model>>> call, Response<BaseResponse<List<Model>>> response) {
                            if (response.code() == 200) {
                                notifys.clear();
                                notifys.addAll(response.body().getData());
                                mAdapterNotify.notifyChange();
                                baseView.hideLoading();
                            } else {
                                onFailure(null, null);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<List<Model>>> call, Throwable t) {
                            baseView.hideLoading();
                        }
                    });

    }
    public void loadTrans(){
        listTrans.clear();
        mAdapterTrans.notifyChange();
        baseView.showLoading();
        MedTedInstance.getInstance().historyTransPay(DataUtils.getToken(mContext))
                .enqueue(new Callback<BaseResponse<List<Model>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<Model>>> call, Response<BaseResponse<List<Model>>> response) {
                        if (response.code() == 200) {
                            listTrans.clear();
                            listTrans.addAll(response.body().getData());
                            mAdapterTrans.notifyChange();
                            baseView.hideLoading();
                        } else {
                            onFailure(null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<Model>>> call, Throwable t) {
                        baseView.hideLoading();
                    }
                });

    }


}
