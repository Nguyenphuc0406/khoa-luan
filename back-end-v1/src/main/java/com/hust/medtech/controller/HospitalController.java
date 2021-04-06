package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.dto.HospitalDTO;
import com.hust.medtech.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HospitalController {
    @Autowired
    HospitalService hospitalService;

    @PostMapping(path = ConfigUrl.URL_HOSPITAL)
    public BaseResponse addHospital(@RequestBody HospitalDTO hospitalDTO) {
        return hospitalService.addHospital(hospitalDTO);
    }

    @GetMapping(path = ConfigUrl.URL_HOSPITAL + "/{id}")
    public BaseResponse getHospital(@PathVariable("id") int hospitalId) {
        return hospitalService.getHospitalById(hospitalId);
    }
}
