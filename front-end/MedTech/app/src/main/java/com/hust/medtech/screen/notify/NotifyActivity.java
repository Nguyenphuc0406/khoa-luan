package com.hust.medtech.screen.notify;

import com.hust.medtech.R;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.databinding.ActivityNotifyBinding;

public class NotifyActivity extends BaseActivity<ActivityNotifyBinding,NotifyActivity> {
    @Override
    public int layoutId() {
        return R.layout.activity_notify;
    }

    @Override
    public void initData() {
        mBinding.setPresenter(this);
        mBinding.id.getSettings().setJavaScriptEnabled(true);
        // Load local HTML from url
        mBinding.id.loadUrl(getIntent().getStringExtra("url"));
    }
}
