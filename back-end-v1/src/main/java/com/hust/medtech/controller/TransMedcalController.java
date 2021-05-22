package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.dto.TransactionMedicalDTO;
import com.hust.medtech.service.TransactionMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransMedcalController {
    @Autowired
    TransactionMedicalService transMedService;

    @PostMapping(path = ConfigUrl.URL_CHI_DINH_KHAM)
    public BaseResponse addTransMed(@RequestBody TransactionMedicalDTO transactionMedicalDTO) {
        String doctorName = SecurityContextHolder.getContext().getAuthentication().getName();
        return transMedService.addTransMedical(transactionMedicalDTO, doctorName);
    }

    @GetMapping(path = ConfigUrl.URL_CHI_DINH_KHAM_GET_ID)
    public BaseResponse getTotalPayment() {
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        return transMedService.getTotalPayment(patientName);
    }

}
