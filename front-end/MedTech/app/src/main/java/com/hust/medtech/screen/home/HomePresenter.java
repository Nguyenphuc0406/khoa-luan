package com.hust.medtech.screen.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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
import com.hust.medtech.api.response.GetNewsResponse;
import com.hust.medtech.api.response.UserInfoResponse;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.CallBack;
import com.hust.medtech.model.Notify;
import com.hust.medtech.model.Role;
import com.hust.medtech.model.UserInfo;
import com.hust.medtech.screen.PaymentActivity;
import com.hust.medtech.screen.doctormed.DoctorMedActivity;
import com.hust.medtech.screen.notify.NotifyActivity;
import com.hust.medtech.screen.requestmed.RequestMedTechActivity;
import com.hust.medtech.screen.requestmed.fragment.RequestStep1Fragment;
import com.hust.medtech.screen.todo.TodoCustomerActivity;
import com.hust.medtech.utils.DataUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class HomePresenter {
    private Context mContext;
    public ObservableInt type;
    public ObservableBoolean isDoctor;
    public ObservableField<String> username;
    public ObservableField<BaseRecyclerview<Notify>> mAdapter;


private  List<Notify> strings;



    public HomePresenter(Context mContext) {
        this.mContext = mContext;
        initData();
    }
    private void initData(){
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

                        // Log and toast

                        Log.d("TOKEN : ", token);

                    }
                });


        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);

        type = new ObservableInt();
        strings = new ArrayList<>();
        isDoctor= new ObservableBoolean();
        UserInfo userInfo = DataUtils.getUserInfo(mContext);
        if(Role.ROLE_ADMIN.equals(userInfo.getRole())){
            isDoctor.set(true);
            username = new ObservableField("Bác sĩ: "+DataUtils.getUserInfo(mContext).getFullName());
        }else {
            username = new ObservableField("Bệnh nhân: "+DataUtils.getUserInfo(mContext).getFullName());

        }
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

        }else  if(type ==2){
            mContext.startActivity(new Intent(mContext, PaymentActivity.class));
//           requestPayment();

        }else if( type == 3){
            mContext.startActivity(new Intent(mContext, TodoCustomerActivity.class));

        }
        else if( type == 4){
            mContext.startActivity(new Intent(mContext, DoctorMedActivity.class));

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
