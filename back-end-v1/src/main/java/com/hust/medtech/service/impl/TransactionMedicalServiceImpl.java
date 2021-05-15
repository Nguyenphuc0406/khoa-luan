package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.TransactionMedicalDTO;
import com.hust.medtech.data.entity.*;
import com.hust.medtech.data.entity.key.TransactionMedicalDetailID;
import com.hust.medtech.repository.*;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.TransactionMedicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionMedicalServiceImpl implements TransactionMedicalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMedicalServiceImpl.class);
    @Autowired
    TransactionMedicalRepository medicalRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    TransMedDetailRepository medicalDetailRepository;
    @Autowired
    IODRepository iodRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtTokenProvider tokenProvider;

    // danh cho BS dang ky chi muc
    @Override
    public BaseResponse addTransMedical(TransactionMedicalDTO transMedicalDTO, String token) {
        if (transMedicalDTO != null && transMedicalDTO.getItemOfDepts() != null) {
            // kiem tra tai khoan nguoi dung
            Account accPatientCheck = accountRepository.findByUsername(transMedicalDTO.getPatientName());
            if (accPatientCheck != null) {
                String accNameByToken = tokenProvider.getUserNameFromJWT(token);
                LOGGER.info("Bac sy " + accNameByToken + " dang tao phieu kham");
                // get Doctor by token
                // BS can login
                Account accDoctor = accountRepository.findByUsername(accNameByToken);
                Doctor doctorC = doctorRepository.findByAccount(accDoctor);

                Patient patientC = patientRepository.findByAccount_AccountId(accPatientCheck.getAccountId());
                TransactionMedical transactionMedical = TransactionMedical.builder()
                        .createDate(new Date())
                        .doctor(doctorC)
                        .status(1)
                        .patient(patientC).build();
                TransactionMedical medical = medicalRepository.save(transactionMedical);
                LOGGER.info("BS " + accDoctor.getUsername() + " tao don kham cho benh nhan " + accPatientCheck.getUsername() + " thanh cong");
                List<TransactionMedicalDetail> details = new ArrayList<>();
                for (Integer i : transMedicalDTO.getItemOfDepts()) {
                    details.add(new TransactionMedicalDetail(new TransactionMedicalDetailID(medical.getTransId(), i)));
                }
                medicalDetailRepository.saveAll(details);
                LOGGER.info("Luu du lieu phieu kham thanh cong cho benh nhan " + accPatientCheck.getUsername());
                return new OkResponse(MsgRespone.TAO_PHIEU_KHAM_THANH_CONG);
            } else {
                return new NotFoundResponse(MsgRespone.PATIENT_NOT_FOUND);
            }


        } else {
            return new BadResponse(MsgRespone.TAO_PHIEU_KHAM_THAT_BAI);
        }
    }


}
