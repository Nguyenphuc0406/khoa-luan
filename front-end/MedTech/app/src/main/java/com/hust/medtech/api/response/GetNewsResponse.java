package com.hust.medtech.api.response;

import com.hust.medtech.model.Notify;

import java.util.List;

public class GetNewsResponse {
    private String code;
    private String msg;
    private List<Notify> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Notify> getData() {
        return data;
    }

    public void setData(List<Notify> data) {
        this.data = data;
    }
}
