package com.hust.medtech.api.response;

import com.hust.medtech.model.UserInfo;

public class UserInfoResponse {
    private String code;
    private String msg;
    private UserInfo data;

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

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
