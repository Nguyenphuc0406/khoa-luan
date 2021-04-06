package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.ItemOfDeptDTO;
import com.hust.medtech.data.entity.Dept;
import com.hust.medtech.data.entity.ItemOfDept;
import com.hust.medtech.data.request.DeptIdRequest;
import com.hust.medtech.repository.DeptRepository;
import com.hust.medtech.repository.IODRepository;
import com.hust.medtech.service.IODService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IODServiceImpl implements IODService {
    @Autowired
    IODRepository iodRepository;

    @Autowired
    DeptRepository deptRepository;

    @Override
    public BaseResponse addItemOfDept(ItemOfDeptDTO itemOfDeptDTO) {
        if (itemOfDeptDTO != null && itemOfDeptDTO.getDept().getDeptId() != null) {
            Dept deptCheck = deptRepository.findByDeptId(itemOfDeptDTO.getDept().getDeptId());
            if (deptCheck != null) {
                Dept dept = Dept.builder()
                        .deptId(itemOfDeptDTO.getDept().getDeptId()).build();
                ItemOfDept itemOfDept = ItemOfDept.builder()
                        .name(itemOfDeptDTO.getName())
                        .consultingRoom(itemOfDeptDTO.getConsultingRoom())
                        .price(itemOfDeptDTO.getPrice())
                        .description(itemOfDeptDTO.getDescription())
                        .code(itemOfDeptDTO.getCode())
                        .dept(dept).build();
                iodRepository.save(itemOfDept);
                return new OkResponse(MsgRespone.IOD_ADD_SUCCESS);
            } else {
                return new NotFoundResponse(MsgRespone.DEPT_NOT_FOUND);
            }
        } else {
            return new BadResponse(MsgRespone.IOD_INPUT_INVALID);
        }


    }

    @Override
    public BaseResponse getAllIodByDeptId(DeptIdRequest deptId) {
        Dept dept = deptRepository.findByDeptId(deptId.getDeptId());
        if (deptId != null || dept == null) {
            List<ItemOfDept> itemOfDepts = iodRepository.findByDept(dept);
            return new OkResponse(itemOfDepts);
        } else {
            return new NotFoundResponse(MsgRespone.GLOBAL_INPUT_INVALID);
        }

    }
}
