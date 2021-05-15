package com.hust.medtech.screen.requestmed.fragment;

import android.os.Bundle;

import com.hust.medtech.R;
import com.hust.medtech.base.BaseFragment;
import com.hust.medtech.databinding.FragmentRequestStep2Binding;
import com.hust.medtech.utils.DataUtils;

public class RequestStep2Fragment extends BaseFragment<FragmentRequestStep2Binding, RequestStep2Fragment> {

    public static RequestStep2Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        RequestStep2Fragment fragment = new RequestStep2Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int layoutId() {
        return R.layout.fragment_request_step2;
    }

    @Override
    public void initData() {
        mBinding.setPresenter(this);
        mBinding.setUser(DataUtils.getUserInfo(getActivity()));
    }

    public void onCancel(){
        getActivity().onBackPressed();
    }
    public void gotoStep3(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fm_main, RequestStep3Fragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
