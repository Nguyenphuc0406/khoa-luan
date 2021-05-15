package com.hust.medtech.screen.home;

import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.hust.medtech.R;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomePresenter> {

    @Override
    public int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initData() {
        mPresenter = new HomePresenter(this);
        mBinding.setPresenter(mPresenter);
        setupMenu();
        actionViewFlipper();
    }

    private void setupMenu() {
        AHBottomNavigation bottomNavigation = mBinding.AHBottomNavigation;
        mBinding.AHBottomNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Trang chủ",
                R.drawable.ic_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Lịch sử",
                R.drawable.ic_timeline_24);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Thông báo",
                R.drawable.ic_notifications);

        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Cài đặt",
                R.drawable.ic_baseline_settings_24);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);


        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

// Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#54c0f9"));
        bottomNavigation.setInactiveColor(Color.parseColor("#7998af"));

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);


// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int p, boolean wasSelected) {


                return true;
            }
        });

// Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

// Add or remove notification for each item
        bottomNavigation.setNotification("1", 2);
// OR


// Enable / disable item & set disable color
//        bottomNavigation.enableItemAtPosition(2);
//        bottomNavigation.disableItemAtPosition(2);

    }

    private void actionViewFlipper() {
        ViewFlipper viewFlipper = mBinding.viewlipper;

        List<Integer> dataImage =  new ArrayList<>();
        dataImage.add(R.drawable.banner2);
        dataImage.add(R.drawable.banner1);

        for (Integer i :dataImage){
            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(i);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setAnimation(animation_slide_out);
    }
}
