package com.hust.medtech.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<M extends ViewDataBinding,P> extends AppCompatActivity {
    protected M mBinding;
    protected P mPresenter;
    protected LoadingDialog mLoadingDialog;



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

    /**
     * Show loading dialog without leak window
     */


    public void showLoadingDialog() {
        try {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                            mLoadingDialog.dismiss();
                            mLoadingDialog = null;
                        }

                        if (mLoadingDialog == null) {
                            mLoadingDialog = new LoadingDialog(BaseActivity.this);
                        }
                        mLoadingDialog.show();

                    } catch (Exception e) {

                    }


                }
            });
        } catch (Exception e) {

        }


    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    /**
     * Hide loading dialog, with check activity working or not
     */
    public void hideLoadingDialog() {
        try {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mLoadingDialog == null || !mLoadingDialog.isShowing()) {
                            return;
                        }
                        mLoadingDialog.dismiss();
                    } catch (Exception e) {

                    }

                }
            });
        } catch (Exception e) {

        }


    }


}
