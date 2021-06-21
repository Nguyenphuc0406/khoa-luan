package com.hust.medtech.screen.login;

import com.hust.medtech.R;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.base.BaseView;
import com.hust.medtech.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginPresenter> implements BaseView {

    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        mPresenter = new LoginPresenter(this,this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }
}
