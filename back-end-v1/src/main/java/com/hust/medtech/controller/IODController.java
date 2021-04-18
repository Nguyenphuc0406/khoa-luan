package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.dto.ItemOfDeptDTO;
import com.hust.medtech.data.request.DeptIdRequest;
import com.hust.medtech.data.request.FindIODName;
import com.hust.medtech.service.IODService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IODController {
    @Autowired
    IODService iodService;

    @PostMapping(path = ConfigUrl.URL_ITEM_OF_DEPT)
    public BaseResponse addItemOfDept(@RequestBody ItemOfDeptDTO itemOfDeptDTO) {
        return iodService.addItemOfDept(itemOfDeptDTO);
    }

    @PostMapping(path = ConfigUrl.URL_GET_IOD_BY_DEPT)
    public BaseResponse getIodByDept(@RequestBody DeptIdRequest request, @RequestHeader("accept-token") String token) {
        return iodService.getAllIodByDeptIdWithHospital(request, token);
    }

    @GetMapping(path = ConfigUrl.URL_ITEM_OF_DEPT + "/{id}")
    public BaseResponse getItemOfDeptById(@PathVariable("id") int iodId) {
        return iodService.getIodById(iodId);
    }

    @GetMapping(path = ConfigUrl.URL_ITEM_OF_DEPT)
    public BaseResponse getAllIod() {
        return iodService.getAllIOD();
    }

    @PostMapping(path = ConfigUrl.URL_ITEM_OF_DEPT + "/find-by-name")
    public BaseResponse getItemOfDeptByName(@RequestBody FindIODName findIODName) {
        return iodService.findIodByNam(findIODName);
    }


}
