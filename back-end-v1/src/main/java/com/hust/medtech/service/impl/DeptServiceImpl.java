package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.DeptDTO;
import com.hust.medtech.data.entity.Dept;
import com.hust.medtech.data.entity.Hospital;
import com.hust.medtech.repository.DeptRepository;
import com.hust.medtech.repository.HospitalRepository;
import com.hust.medtech.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    DeptRepository deptRepository;
    @Autowired
    HospitalRepository hospitalRepository;

    @Override
    public BaseResponse addDept(DeptDTO deptDTO) {
        if (deptDTO != null && deptDTO.getHospital().getHospitalId() != null) {
            Hospital hospitalCheck = hospitalRepository.findByHospitalId(deptDTO.getHospital().getHospitalId());
            if (hospitalCheck != null) {
                Hospital hospital = Hospital.builder()
                        .hospitalId(deptDTO.getHospital().getHospitalId()).build();
                Dept dept = Dept.builder()
                        .name(deptDTO.getName())
                        .phoneNumber(deptDTO.getPhoneNumber())
                        .address(deptDTO.getAddress())
                        .description(deptDTO.getDescription())
                        .hospital(hospital).build();
                deptRepository.save(dept);
                return new OkResponse(MsgRespone.DEPT_ADD_SUCCESS);
            } else {
                return new BadResponse(MsgRespone.GLOBAL_TRUNG_DU_LIEU);
            }

        } else {
            return new BadResponse(MsgRespone.DEPT_ADD_ERROR);
        }
    }

    @Override
    public BaseResponse getAllDept() {
        return null;
    }

    @Override
    public BaseResponse getDeptById(int deptId) {
        return null;
    }
}
