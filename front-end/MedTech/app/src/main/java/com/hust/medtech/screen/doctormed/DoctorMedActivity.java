package com.hust.medtech.screen.doctormed;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.response.BaseResponse;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.CallBack;
import com.hust.medtech.databinding.ActivityDoctorMedBinding;
import com.hust.medtech.model.Model;
import com.hust.medtech.screen.todo.TodoCustomerActivity;
import com.hust.medtech.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorMedActivity extends BaseActivity<ActivityDoctorMedBinding, DoctorMedActivity> {
    public ObservableField<BaseRecyclerview<Model>> mAdapter;
    private List<Model> models;

    @Override
    public int layoutId() {
        return R.layout.activity_doctor_med;
    }

    @Override
    public void initData() {
        models = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(this,models,R.layout.item_patient_pot));
        mBinding.setPresenter(this);
        mAdapter.get().setCallBack(new CallBack<Model>() {
            @Override
            public void onClick(Model item) {
                if(item.getStatus() ==1) return;
                Intent it = new Intent(DoctorMedActivity.this,DetailMedActivity.class);
                it.putExtra("model",new Gson().toJson(item));
                startActivityForResult(it,100);
            }
        });
        getPot(DataUtils.getToken(this));
    }

    public void onCancel() {
        finish();
    }
    private void getPot(String token) {
        models.clear();
        mAdapter.notifyChange();
        showLoadingDialog();
        MedTedInstance.getInstance().getPatientMedicalByDay(token)
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
                        hideLoadingDialog();
                        Toast.makeText(DoctorMedActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            getPot(DataUtils.getToken(this));
        }
    }
}
