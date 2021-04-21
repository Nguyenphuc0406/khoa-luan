package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping(path = ConfigUrl.URL_DATA_PAYMENT)
    public BaseResponse getDataPayment(@RequestHeader("accept-token") String token) {
        return paymentService.getDataPayment(token);
    }

}
