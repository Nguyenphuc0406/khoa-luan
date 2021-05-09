package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.PaymentDTO;
import com.hust.medtech.data.entity.*;
import com.hust.medtech.data.entity.response.DataPayment;
import com.hust.medtech.repository.*;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    TransactionMedicalRepository tranMedRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    IODRepository iodRepository;
    @Autowired
    TransMedDetailRepository transMedDetailRepository;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public BaseResponse getDataPayment(String token) {
        // get data patient
        String patientName = tokenProvider.getUserNameFromJWT(token);
        Account accountParent = accountRepository.findByUsername(patientName);
        Patient patient = patientRepository.findByAccountId(accountParent.getAccountId());
        // tim phieu kham tu BS  ung voi benh nhan va status =0 : chua thanh toan
        TransactionMedical transactionMedical = tranMedRepository.findByPatientAndStatus(patient, 0);
        try {
            List<Integer> iodByPatient = transMedDetailRepository.getListIodByTransId(transactionMedical.getTransId());
            if (iodByPatient != null) {
                List<ItemOfDept> iodepts = new ArrayList<>();
                int totalPrice = 0;
                for (Integer i : iodByPatient) {
                    ItemOfDept itemOfDept = iodRepository.findByIodId(i);
                    if (itemOfDept != null) {
                        iodepts.add(itemOfDept);
                        totalPrice += itemOfDept.getPrice();
                    } else {
                        LOGGER.info("Khong tim thay IOD tuong ung voi patient");
                    }
                }
                DataPayment dataPayment = DataPayment.builder()
                        .transMedId(transactionMedical.getTransId())
                        .namePatient(patientName)
                        .itemOfDepts(iodepts)
                        .totalPrice(totalPrice).build();


                return new OkResponse(dataPayment);
            } else {
                return new NotFoundResponse(MsgRespone.DATA_PAYMENT_NOT_FOUNT);
            }
        } catch (Exception e) {
            LOGGER.error("Loi tim du lieu thanh toan: " + e);
        }
        return new BadResponse(MsgRespone.GLOBAL_DATA_NOT_FOUND);

    }

    @Override
    public BaseResponse payment(PaymentDTO paymentDTO, String token) {
        return null;
    }
}
