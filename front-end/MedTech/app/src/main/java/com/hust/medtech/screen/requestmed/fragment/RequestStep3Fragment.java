package com.hust.medtech.screen.requestmed.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.request.RegisterMedRequest;
import com.hust.medtech.api.response.EmptyResponse;
import com.hust.medtech.api.response.GetDeptResponse;
import com.hust.medtech.api.response.GetNewsResponse;
import com.hust.medtech.base.BaseFragment;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.CallBack;
import com.hust.medtech.base.DialogNotify;
import com.hust.medtech.databinding.FragmentRequestStep2Binding;
import com.hust.medtech.databinding.FragmentRequestStep3Binding;
import com.hust.medtech.model.Dept;
import com.hust.medtech.model.Notify;
import com.hust.medtech.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestStep3Fragment extends BaseFragment<FragmentRequestStep3Binding, RequestStep3Fragment> {
    public ObservableField<BaseRecyclerview<Dept>> mAdapter;
    public ObservableField<String> des;
    private List<Dept> data;
    public static RequestStep3Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        RequestStep3Fragment fragment = new RequestStep3Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int layoutId() {
        return R.layout.fragment_request_step3;
    }

    @Override
    public void initData() {
        des = new ObservableField<>();
        data = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(getActivity(),data,R.layout.item_dept));
        mBinding.setPresenter(this);
        getDept(DataUtils.getToken(getContext()));
        mAdapter.get().setCallBack(new CallBack<Dept>() {
            @Override
            public void onClick(Dept item) {
                if(item.isCheck()){
                    item.setCheck(false);
                }else {
                    item.setCheck(true);
                }

            }
        });
    }
    private void getDept(String token){
        MedTedInstance.getInstance().getDept(token)
                .enqueue(new Callback<GetDeptResponse>() {
                    @Override
                    public void onResponse(Call<GetDeptResponse> call, Response<GetDeptResponse> response) {
                        if (response.code() == 200) {
                            data.addAll(response.body().getData());
                            mAdapter.notifyChange();
                        } else {
                            Toast.makeText(getContext(), "Fail ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDeptResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void onCancel(){
        getActivity().onBackPressed();
    }


    public void registerMed(){
        List<Integer> id = new ArrayList<>();
        for (Dept d : data){
            if(d.isCheck()){
                id.add(d.getId());
            }
        }

        if(id.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng chọn khoa cần khám!", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterMedRequest registerMedRequest = new RegisterMedRequest();
        registerMedRequest.setDepts(id);
        registerMedRequest.setDescription(des.get());
        MedTedInstance.getInstance().registerMed(registerMedRequest,DataUtils.getToken(getContext()))
                .enqueue(new Callback<EmptyResponse>() {
                    @Override
                    public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {
                        if (response.code() == 200) {
                            new DialogNotify(getContext()).show();
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
