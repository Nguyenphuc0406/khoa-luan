package com.hust.medtech.screen.doctormed;

import android.app.Activity;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.request.PotRequest;
import com.hust.medtech.api.request.RegisterMedRequest;
import com.hust.medtech.api.response.BaseResponse;
import com.hust.medtech.api.response.EmptyResponse;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.CallBack;
import com.hust.medtech.base.DialogNotify;
import com.hust.medtech.databinding.ActivityDetailPotBinding;
import com.hust.medtech.databinding.ActivityDoctorMedBinding;
import com.hust.medtech.model.Dept;
import com.hust.medtech.model.Model;
import com.hust.medtech.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMedActivity extends BaseActivity<ActivityDetailPotBinding, DetailMedActivity> {
    public ObservableField<BaseRecyclerview<Dept>> mAdapter;
    private List<Dept> models;
    private Model model;
    public ObservableBoolean isSelected;

    @Override
    public int layoutId() {
        return R.layout.activity_detail_pot;
    }

    @Override
    public void initData() {
        isSelected = new ObservableBoolean();
        model = new Gson().fromJson(getIntent().getStringExtra("model"),Model.class);
        mBinding.setItem(model);
        models = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(this, models, R.layout.item_dept_pot));
        mBinding.setPresenter(this);
        mAdapter.get().setCallBack(new CallBack<Dept>() {
            @Override
            public void onClick(Dept item) {
                if(item.isCheck()){
                    item.setCheck(false);
                }else {
                    item.setCheck(true);
                }
                checkSelect();
            }
        });
        getItemPot(DataUtils.getToken(this));
    }

    private void checkSelect(){
        isSelected.set(false);
        for (Dept d : models){
            if(d.isCheck()){
                isSelected.set(true);
              return;
            }
        }
    }

    public void onCancel() {
        finish();
    }

    private void getItemPot(String token) {
        showLoadingDialog();
        MedTedInstance.getInstance().getItemOfDept(token)
                .enqueue(new Callback<BaseResponse<List<Dept>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<Dept>>> call, Response<BaseResponse<List<Dept>>> response) {
                        if (response.code() == 200) {
                            models.addAll(response.body().getData());
                            mAdapter.notifyChange();
                            hideLoadingDialog();
                        } else {
                            onFailure(null, null);
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<Dept>>> call, Throwable t) {
                        hideLoadingDialog();
                        Toast.makeText(DetailMedActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendRequest(){
        isSelected.set(false);
        showLoadingDialog();
        List<Integer> id = new ArrayList<>();
        for (Dept d : models){
            if(d.isCheck()){
                id.add(d.getIodId());
            }
        }



        PotRequest potRequest = new PotRequest();
        potRequest.setPotId(model.getPotId());
        potRequest.setItemOfDepts(id);
        MedTedInstance.getInstance().requestPot(potRequest,DataUtils.getToken(this))
                .enqueue(new Callback<EmptyResponse>() {
                    @Override
                    public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {
                        if (response.code() == 200) {
                            hideLoadingDialog();
                            new DialogNotify(DetailMedActivity.this, "Bạn đã chỉ định khám cận lâm sàng thành công cho bênh nhân: " +
                                    "" + model.getPatientName() + ".", new DialogNotify.CallBack() {
                                @Override
                                public void onConfirm() {
                                    setResult(Activity.RESULT_OK);
                                    finish();

                                }
                            }).show();
                        } else {
                            onFailure(null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<EmptyResponse> call, Throwable t) {
                        hideLoadingDialog();
                        isSelected.set(true);
                    }
                });
    }
}
