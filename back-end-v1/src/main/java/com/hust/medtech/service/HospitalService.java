package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.HospitalDTO;
import com.hust.medtech.data.dto.PatientDTO;
import com.hust.medtech.data.request.RequestDataForm;

public interface HospitalService {
    BaseResponse addHospital(HospitalDTO hospitalDTO);


    BaseResponse getAllHospital();

    BaseResponse getHospitalById(int hospitalId);

    BaseResponse deleteHospital(int hospitalId);
}
