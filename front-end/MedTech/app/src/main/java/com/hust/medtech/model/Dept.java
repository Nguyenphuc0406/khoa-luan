package com.hust.medtech.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.annotations.SerializedName;

public class Dept extends BaseObservable {
    @SerializedName("deptId")
    private int id;
    private int iodId;
    private String name;
    private String consultingRoom;
    private String description;
    private boolean isCheck;

    @Bindable
    public boolean isCheck() {
        return isCheck;
    }

    public int getIodId() {
        return iodId;
    }

    public void setIodId(int iodId) {
        this.iodId = iodId;
    }

    public String getConsultingRoom() {
        return consultingRoom;
    }

    public void setConsultingRoom(String consultingRoom) {
        this.consultingRoom = consultingRoom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
