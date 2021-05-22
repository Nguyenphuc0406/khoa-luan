package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.ProcessOfTreatmentDTO;

public interface POTService {
    BaseResponse addPOT(ProcessOfTreatmentDTO treatmentDTO, String token);

    BaseResponse getDataMedical(String patientName);
}
