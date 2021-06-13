package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.PaymentDTO;
import com.hust.medtech.data.request.PaymentRequest;

public interface PaymentService {
    BaseResponse getDataMedicalDetail(String patientName, int potId);

    BaseResponse payment(PaymentRequest paymentDTO, String username);

    BaseResponse getPaymentHistory();

    BaseResponse getPaymentDetailByCode(String code);
}
