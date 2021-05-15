package com.hust.medtech.base.response;

import org.springframework.http.HttpStatus;

public class OkResponse <T> extends BaseResponse<T> {

    public OkResponse(T data) {
        super(HttpStatus.OK, HttpStatus.OK.name(), data);
    }
}
