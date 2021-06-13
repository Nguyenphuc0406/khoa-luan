package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;

public interface AccountService {
    BaseResponse registerDeviceToken(String token, String accountName);

    BaseResponse getNotifyByAccount(String accountName);
}
