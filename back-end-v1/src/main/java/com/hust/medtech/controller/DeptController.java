package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.dto.DeptDTO;
import com.hust.medtech.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {
    @Autowired
    DeptService deptService;

    @PostMapping(path = ConfigUrl.URL_DEPT)
    public BaseResponse addDept(@RequestBody DeptDTO deptDTO) {
        return deptService.addDept(deptDTO);
    }

}
