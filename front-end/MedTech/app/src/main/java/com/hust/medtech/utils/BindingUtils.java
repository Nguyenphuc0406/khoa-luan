package com.hust.medtech.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

public class BindingUtils {
    @BindingAdapter(value = {
            "app:adapter", "app:orientation","app:count"
    }, requireAll = false)
    public static void setRecyclerViewData(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation,int count) {
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager;
        Context context = recyclerView.getContext();

        if(count > 1){
            layoutManager = new GridLayoutManager(context,count);
        }else {
            layoutManager = new LinearLayoutManager(context,
                    orientation == 1
                            ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false);
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    @BindingAdapter(value = {
            "app:adapterScrollView"
    }, requireAll = false)
    public static void adapterScrollView(DiscreteScrollView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setOrientation(DSVOrientation.HORIZONTAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemTransitionTimeMillis(150);

        recyclerView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.92f)
                .build());
    }

    @BindingAdapter(value = {
            "app:url"
    }, requireAll = false)
    public static void image(AppCompatImageView recyclerView,String url) {
        Glide.with(recyclerView.getContext())
                .load(url)
                .into(recyclerView);
    }
    @BindingAdapter(value = {
            "app:src"
    }, requireAll = false)
    public static void image(ImageView recyclerView, Drawable re) {
        recyclerView.setImageDrawable(re);
    }
    @BindingAdapter(value = {
            "app:textLink"
    }, requireAll = false)
    public static void setLink(TextView tv ,String str){
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        tv.setText(content);
    }


}
