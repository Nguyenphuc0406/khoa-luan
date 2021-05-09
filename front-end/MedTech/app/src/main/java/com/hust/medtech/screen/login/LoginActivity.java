package com.hust.medtech.screen.login;

import com.hust.medtech.R;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginPresenter> {

    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        mPresenter = new LoginPresenter(this);
        mBinding.setPresenter(mPresenter);
    }
}
