package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.dto.TransactionMedicalDTO;
import com.hust.medtech.service.TransactionMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransMedcalController {
    @Autowired
    TransactionMedicalService transMedService;

    @PostMapping(path = ConfigUrl.URL_PHIEU_KHAM)
    public BaseResponse addTransMed(@RequestBody TransactionMedicalDTO transactionMedicalDTO) {
        return transMedService.addTransMedical(transactionMedicalDTO);
    }

}
