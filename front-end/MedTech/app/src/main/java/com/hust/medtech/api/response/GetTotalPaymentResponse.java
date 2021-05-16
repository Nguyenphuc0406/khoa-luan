package com.hust.medtech.api.response;

import com.hust.medtech.model.Notify;

import java.util.List;

public class GetTotalPaymentResponse {

    private String code;
    private String msg;
    private PaymentResponse data;

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

    public PaymentResponse getData() {
        return data;
    }

    public void setData(PaymentResponse data) {
        this.data = data;
    }
}
