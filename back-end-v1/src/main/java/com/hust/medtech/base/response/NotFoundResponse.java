package com.hust.medtech.base.response;

import org.springframework.http.HttpStatus;

public class NotFoundResponse extends BaseResponse{
    public NotFoundResponse() {
        super(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    public NotFoundResponse(String what) {
        super(HttpStatus.NOT_FOUND,what);
    }
}