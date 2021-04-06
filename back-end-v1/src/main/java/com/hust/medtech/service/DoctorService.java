package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.DoctorDTO;
import com.hust.medtech.data.dto.PatientDTO;
import com.hust.medtech.data.request.RequestDataForm;

public interface DoctorService {
    BaseResponse addDoctor(DoctorDTO doctorDTO);

    BaseResponse getAllDoctor();

    BaseResponse getDoctorById(int doctorId);

    BaseResponse deleteDoctor(int doctorId);

}
