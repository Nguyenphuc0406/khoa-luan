package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.DeptDTO;

public interface DeptService {
    BaseResponse addDept(DeptDTO deptDTO);

    BaseResponse getAllDept();

    BaseResponse getDeptById(int deptId);
}
