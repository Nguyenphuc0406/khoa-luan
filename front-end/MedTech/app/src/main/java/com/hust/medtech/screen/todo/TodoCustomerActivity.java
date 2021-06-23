package com.hust.medtech.screen.todo;

import android.content.Intent;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.response.BaseResponse;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.CallBack;
import com.hust.medtech.databinding.ActivityTodoCustomerBinding;
import com.hust.medtech.model.Model;
import com.hust.medtech.screen.doctormed.DetailMedActivity;
import com.hust.medtech.screen.doctormed.DoctorMedActivity;
import com.hust.medtech.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoCustomerActivity extends BaseActivity<ActivityTodoCustomerBinding,TodoCustomerActivity> {
    public ObservableField<BaseRecyclerview<Model>> mAdapter;
    private List<Model> models;

    @Override
    public int layoutId() {
        return R.layout.activity_todo_customer;
    }

    @Override
    public void initData() {
        models = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(this,models,R.layout.item_todo));
        getTodoList(DataUtils.getToken(this));
        mBinding.setPresenter(this);
        mAdapter.get().setCallBack(new CallBack<Model>() {
            @Override
            public void onClick(Model item) {
                if(item.getIsDoctorAccepted() == 0) return;
                Intent it = new Intent(TodoCustomerActivity.this, ChiTietKhamActivity.class);
                it.putExtra("model",new Gson().toJson(item));
                startActivityForResult(it,100);
            }
        });
    }

    private void getTodoList(String token) {
        showLoadingDialog();
        MedTedInstance.getInstance().getTodoList(token)
                .enqueue(new Callback<BaseResponse<List<Model>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<Model>>> call, Response<BaseResponse<List<Model>>> response) {
                        if (response.code() == 200) {
                            models.addAll(response.body().getData());
                            mAdapter.notifyChange();
                            hideLoadingDialog();
                        } else {
                            onFailure(null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<Model>>> call, Throwable t) {
                        showLoadingDialog();
                        Toast.makeText(TodoCustomerActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onCancel() {
        super.onCancel();
    }
}
