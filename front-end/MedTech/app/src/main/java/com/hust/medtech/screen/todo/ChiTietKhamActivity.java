package com.hust.medtech.screen.todo;

import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.response.BaseResponse;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.databinding.ActivityChiTietKhamBinding;
import com.hust.medtech.databinding.ActivityTodoCustomerBinding;
import com.hust.medtech.model.Model;
import com.hust.medtech.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietKhamActivity extends BaseActivity<ActivityChiTietKhamBinding,ChiTietKhamActivity> {
    public ObservableField<BaseRecyclerview<Model>> mAdapter;
    private List<Model> models;
    private Model model;
    @Override
    public int layoutId() {
        return R.layout.activity_chi_tiet_kham;
    }

    @Override
    public void initData() {
        models = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(this,models,R.layout.item_chitiet_kham));
        model = new Gson().fromJson(getIntent().getStringExtra("model"),Model.class);
        mBinding.setItem(model);
        getDetailPot(model.getPotId(),model.getDeptId(),DataUtils.getToken(this));
        mBinding.setPresenter(this);
    }

    private void getDetailPot(int potId,int deptId,String token) {
        showLoadingDialog();
        MedTedInstance.getInstance().getDetailPot(potId,deptId,token)
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
                        Toast.makeText(ChiTietKhamActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onCancel() {
        super.onCancel();
    }
}
