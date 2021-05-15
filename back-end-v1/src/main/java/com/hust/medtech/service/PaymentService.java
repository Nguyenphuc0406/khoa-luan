package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.PaymentDTO;

public interface PaymentService {
    BaseResponse getDataPayment(String token);

    BaseResponse payment(PaymentDTO paymentDTO, String token);
}
