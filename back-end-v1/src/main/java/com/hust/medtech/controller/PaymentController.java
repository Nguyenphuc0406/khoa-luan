package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.request.PaymentRequest;
import com.hust.medtech.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping(path = ConfigUrl.URL_PAYMENT_HIST)
    public BaseResponse getPaymentHistory() {
        return paymentService.getPaymentHistory();
    }

    @GetMapping(path = ConfigUrl.URL_PAYMENT_HIST + "/{code}")
    public BaseResponse getPaymentDetailByCode(@PathVariable("code") String code) {
        return paymentService.getPaymentDetailByCode(code);
    }

    @PostMapping(path = ConfigUrl.URL_DATA_PAYMENT)
    public BaseResponse payment(@RequestBody PaymentRequest request) {
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        return paymentService.payment(request, patientName);
    }

    @GetMapping("/historyTransPay")
    public BaseResponse historyTransPay() {
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        return paymentService._getPayTrans( patientName);
    }


}
