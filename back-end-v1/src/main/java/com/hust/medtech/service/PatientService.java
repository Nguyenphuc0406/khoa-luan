package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.PatientDTO;
import com.hust.medtech.data.request.RequestDataForm;

public interface PatientService {
    BaseResponse addPatient(PatientDTO patientDTO);

    BaseResponse submitForm(RequestDataForm dataForm);

    BaseResponse getAllPatient();

    BaseResponse getPatientById(int patientId);

    BaseResponse deletePatient(int patientId);

}
