package com.hust.medtech.base;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.hust.medtech.R;
import com.hust.medtech.databinding.DialogLoadingBinding;


public class LoadingDialog extends Dialog {
    private DialogLoadingBinding mBinding;
    private ObjectAnimator rotationXAnimator;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.DialogLoading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_loading, null, true);
        setContentView(mBinding.getRoot());
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        mBinding.setDialog(this);




    }

    @Override
    public void show() {
        super.show();
        try {
            if(rotationXAnimator == null){
                rotationXAnimator = ObjectAnimator.ofFloat(mBinding.loading,
                        View.ROTATION,
                        0f, 360f);
                rotationXAnimator.setDuration(1000);
                rotationXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
                rotationXAnimator.setRepeatMode(ValueAnimator.RESTART);
            }
            if(!rotationXAnimator.isRunning()){
                rotationXAnimator.start();
            }

        }catch (Exception e){

        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        try {
            if(rotationXAnimator != null && rotationXAnimator.isRunning()){
                rotationXAnimator.end();
            }

        }catch (Exception e){

        }
    }
}