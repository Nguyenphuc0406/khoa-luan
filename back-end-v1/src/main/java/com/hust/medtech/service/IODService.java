package com.hust.medtech.service;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.data.dto.ItemOfDeptDTO;
import com.hust.medtech.data.request.DeptIdRequest;
import com.hust.medtech.data.request.FindIODName;

public interface IODService {

    BaseResponse addItemOfDept(ItemOfDeptDTO itemOfDeptDTO);

    BaseResponse getAllIodByDeptIdWithHospital(DeptIdRequest deptId, String token);

    BaseResponse getAllIOD();

    BaseResponse getIodById(Integer iodId);

    BaseResponse findIodByNam(FindIODName findIODName);
}
