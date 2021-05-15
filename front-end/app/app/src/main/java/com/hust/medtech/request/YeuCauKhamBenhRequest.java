package com.hust.medtech.request;

import java.util.List;

public class YeuCauKhamBenhRequest {
    private String username;
    private List<Integer> danhmucKhamId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getDanhmucKhamId() {
        return danhmucKhamId;
    }

    public void setDanhmucKhamId(List<Integer> danhmucKhamId) {
        this.danhmucKhamId = danhmucKhamId;
    }
}
