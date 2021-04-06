package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.HospitalDTO;
import com.hust.medtech.data.dto.PatientDTO;
import com.hust.medtech.data.entity.Hospital;
import com.hust.medtech.data.request.RequestDataForm;

import java.util.List;

public interface HospitalService {
    BaseResponse addHospital(HospitalDTO hospitalDTO);


    List<Hospital> getAllHospital();

    BaseResponse getHospitalById(int hospitalId);

    BaseResponse deleteHospital(int hospitalId);
}
