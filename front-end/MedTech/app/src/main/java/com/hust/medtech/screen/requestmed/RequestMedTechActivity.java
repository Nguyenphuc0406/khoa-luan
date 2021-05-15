package com.hust.medtech.screen.requestmed;

import android.app.FragmentManager;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hust.medtech.R;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.databinding.ActivityRequestMedBinding;
import com.hust.medtech.screen.requestmed.fragment.RequestStep1Fragment;

public class RequestMedTechActivity extends BaseActivity<ActivityRequestMedBinding,RequestMedTechActivity> {
    @Override
    public int layoutId() {
        return R.layout.activity_request_med;
    }

    @Override
    public void initData() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fm_main, RequestStep1Fragment.newInstance())
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fm = getSupportFragmentManager().findFragmentById(R.id.fm_main);
        fm.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onBackPressed() {
        try {
            FragmentManager mgr = getFragmentManager();
            if (((FragmentManager) mgr).getBackStackEntryCount() == 0) {
                // No backStack to pop, so calling super
                super.onBackPressed();
            } else {
                mgr.popBackStack();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment fm = getSupportFragmentManager().findFragmentById(R.id.fm_main);
        fm.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
