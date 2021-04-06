package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.TransactionMedicalDTO;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.repository.TransactionMedicalRepository;
import com.hust.medtech.service.TransactionMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMedicalServiceImpl implements TransactionMedicalService {
    @Autowired
    TransactionMedicalRepository medicalRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public BaseResponse addTransMedical(TransactionMedicalDTO transMedicalDTO) {
        return null;
    }
}
