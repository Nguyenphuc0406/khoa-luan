package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.ItemOfDeptDTO;
import com.hust.medtech.data.request.DeptIdRequest;

public interface IODService {

    BaseResponse addItemOfDept(ItemOfDeptDTO itemOfDeptDTO);

    BaseResponse getAllIodByDeptIdWithHospital(DeptIdRequest deptId, String token);

    BaseResponse getAllIOD();
}
