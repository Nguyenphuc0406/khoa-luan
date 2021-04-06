package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.HospitalDTO;
import com.hust.medtech.data.entity.Hospital;
import com.hust.medtech.repository.HospitalRepository;
import com.hust.medtech.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    HospitalRepository hospitalRepository;

    @Override
    public BaseResponse addHospital(HospitalDTO hospitalDTO) {
        Hospital hospitalCheck = hospitalRepository.findByName(hospitalDTO.getName());
        if (hospitalCheck == null) {
            if (hospitalDTO != null) {
                Hospital hospital = Hospital.builder()
                        .name(hospitalDTO.getName())
                        .address(hospitalDTO.getAddress())
                        .phoneNumber(hospitalDTO.getPhoneNumber()).build();
                hospitalRepository.save(hospital);
                return new OkResponse(MsgRespone.HOSPITAL_ADD_SUCCESS);
            } else {
                return new BadResponse(MsgRespone.HOSPITAL_INPUT_INVALID);
            }
        } else {
            return new NotFoundResponse(MsgRespone.GLOBAL_TRUNG_DU_LIEU);
        }

    }

    @Override
    public BaseResponse getAllHospital() {
        return null;
    }

    @Override
    public BaseResponse getHospitalById(int hospitalId) {
        return null;
    }

    @Override
    public BaseResponse deleteHospital(int hospitalId) {
        return null;
    }
}
