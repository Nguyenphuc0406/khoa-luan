package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.PaymentDTO;
import com.hust.medtech.data.request.PaymentRequest;

public interface PaymentService {
    BaseResponse getDataPayment(String token);

    BaseResponse payment(PaymentRequest paymentDTO,String username);
}
