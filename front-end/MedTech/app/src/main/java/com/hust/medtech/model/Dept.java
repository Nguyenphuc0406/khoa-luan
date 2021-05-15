package com.hust.medtech.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.annotations.SerializedName;

public class Dept extends BaseObservable {
    @SerializedName("deptId")
    private int id;
    private String name;
    private boolean isCheck;

    @Bindable
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        notifyPropertyChanged(BR.check);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
