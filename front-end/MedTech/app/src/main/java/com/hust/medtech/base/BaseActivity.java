package com.hust.medtech.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<M extends ViewDataBinding,P> extends AppCompatActivity {
    protected M mBinding;
    protected P mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,layoutId());
        initData();
    }

    public abstract int layoutId();
    public abstract void initData();

    public void onCancel(){
        finish();
    }
}
