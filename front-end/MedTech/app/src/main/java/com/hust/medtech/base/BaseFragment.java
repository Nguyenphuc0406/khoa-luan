package com.hust.medtech.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<M extends ViewDataBinding,P> extends Fragment {
    protected M mBinding;
    protected P mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,layoutId(), container, false);
        View view = mBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        initData();
        return view;
    }

    public abstract int layoutId();
    public abstract void initData();

    public void showLoadingDialog() {
        ((BaseActivity) getActivity()).showLoadingDialog();
    }
    public void hideLoading() {
        ((BaseActivity) getActivity()).hideLoadingDialog();
    }


}
