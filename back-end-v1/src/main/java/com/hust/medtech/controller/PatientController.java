package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.dto.PatientDTO;
import com.hust.medtech.data.dto.ProcessOfTreatmentDTO;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.data.entity.response.ResponseDataPOT;
import com.hust.medtech.repository.POTRepository;
import com.hust.medtech.security.CustomUserDetails;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.POTService;
import com.hust.medtech.service.PatientService;
import com.hust.medtech.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    POTService potService;

    @PostMapping(path = ConfigUrl.URL_PATIENT)
    public BaseResponse addPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.addPatient(patientDTO);
    }

    @PostMapping(path = ConfigUrl.URL_PHIEU_KHAM)
    public BaseResponse addRegister(@RequestBody ProcessOfTreatmentDTO dataPOT) {
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        return potService.addPOT(dataPOT, patientName);
    }

    @GetMapping(path = ConfigUrl.URL_LICH_KHAM)
    public BaseResponse addRegister() {
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        return potService.getDataMedical(patientName);
    }


}
