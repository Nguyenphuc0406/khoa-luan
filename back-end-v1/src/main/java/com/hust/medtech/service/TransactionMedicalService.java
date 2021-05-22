package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.TransactionMedicalDTO;

public interface TransactionMedicalService {
    BaseResponse addTransMedical(TransactionMedicalDTO transMedicalDTO, String doctorName);
    BaseResponse getTotalPayment(String userName);
}
