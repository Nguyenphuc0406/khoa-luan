package com.hust.medtech.base.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponse<T> extends ResponseEntity<BaseResponse.ResponseBody<T>> {
    public BaseResponse(HttpStatus status, String msg, T data) {
        super(new ResponseBody<>(status.value(), msg, data), status);
    }

    public BaseResponse(HttpStatus status, String msg) {
        this(status, msg, null);
    }

    public BaseResponse(int speciaklCode, HttpStatus status, String msg, T data) {
        super(new ResponseBody<>(speciaklCode, msg, data), status);
    }

    public static class ResponseBody<T> {
        private int code;
        private String msg;
        private T data;


        public ResponseBody(int code, String msg, T data) {
            super();
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public ResponseBody(int code, String msg) {
            super();
            this.code = code;
            this.msg = msg;

        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }


    }
}