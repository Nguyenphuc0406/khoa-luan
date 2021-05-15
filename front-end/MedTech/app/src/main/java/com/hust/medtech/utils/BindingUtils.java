package com.hust.medtech.utils;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

public class BindingUtils {
    @BindingAdapter(value = {
            "app:adapter", "app:orientation"
    }, requireAll = false)
    public static void setRecyclerViewData(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager;
        Context context = recyclerView.getContext();
        layoutManager = new LinearLayoutManager(context,
                orientation == 1
                        ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false);
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


}
