package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.DeptDTO;
import com.hust.medtech.data.entity.Dept;
import com.hust.medtech.repository.DeptRepository;
import com.hust.medtech.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    DeptRepository deptRepository;


    @Override
    public BaseResponse addDept(DeptDTO deptDTO) {
        if (deptDTO != null) {

            Dept dept = Dept.builder()
                    .name(deptDTO.getName())
                    .phoneNumber(deptDTO.getPhoneNumber())
                    .address(deptDTO.getAddress())
                    .description(deptDTO.getDescription())
                    .build();
            deptRepository.save(dept);
            return new OkResponse(MsgRespone.DEPT_ADD_SUCCESS);


        } else {
            return new BadResponse(MsgRespone.DEPT_ADD_ERROR);
        }
    }

    @Override
    public BaseResponse getAllDept() {
        List<Dept> depts = deptRepository.findAll();
        List<DeptDTO> deptDTOS = new ArrayList<>();
        for (Dept d : depts) {
            DeptDTO deptDTO = DeptDTO.builder()
                    .deptId(d.getDeptId())
                    .address(d.getAddress())
                    .description(d.getDescription())
                    .phoneNumber(d.getPhoneNumber())
                    .name(d.getName()).build();
            deptDTOS.add(deptDTO);
        }

        return new OkResponse(deptDTOS);
    }

    @Override
    public BaseResponse getDeptById(int deptId) {
        if (deptId != 0) {
            Dept dept = deptRepository.findByDeptId(deptId);

            return new OkResponse(dept);
        } else {
            return new BadResponse(MsgRespone.GLOBAL_INPUT_INVALID);
        }
    }
}
