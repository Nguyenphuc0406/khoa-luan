package com.hust.medtech.api.response;

import com.hust.medtech.model.Dept;

import java.util.List;

public class GetDeptResponse {
    private String code;
    private String msg;
    private List<Dept> data;

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

    public List<Dept> getData() {
        return data;
    }

    public void setData(List<Dept> data) {
        this.data = data;
    }
}
