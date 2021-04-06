package com.hust.medtech.base.response;

import org.springframework.http.HttpStatus;

public class BadResponse extends BaseResponse {
    public BadResponse(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadResponse() {
        super(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}
