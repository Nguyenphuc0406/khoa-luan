package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.ProcessOfTreatmentDTO;
import com.hust.medtech.data.entity.*;
import com.hust.medtech.data.entity.key.ProcessOfTreatmentDetailID;
import com.hust.medtech.data.entity.response.ResponseDataPOT;
import com.hust.medtech.repository.*;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.POTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class POTServiceImpl implements POTService {

    private static final Logger LOGGER = LoggerFactory.getLogger(POTServiceImpl.class);

    @Autowired
    POTRepository potRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    POTDetailRepository potDetailRepository;

    @Autowired
    DeptRepository deptRepository;

    @Override
    public BaseResponse addPOT(ProcessOfTreatmentDTO treatmentDTO, String token) {
        if (treatmentDTO != null) {
            String patientName = tokenProvider.getUserNameFromJWT(token);
            Account accountParent = accountRepository.findByUsername(patientName);
            Patient patient = patientRepository.findByAccountId(accountParent.getAccountId());
            LocalDateTime dateTime = LocalDateTime.now();
            ProcessOfTreatment process = ProcessOfTreatment.builder()
                    .description(treatmentDTO.getDescription())
                    .dateOfExamination(dateTime)
                    .patientPot(patient).build();
            potRepository.save(process);
            // ghi cac giao dich vao ban ghi
            List<ProcessOfTreatmentDetail> details = new ArrayList<>();
            for (Integer i : treatmentDTO.getDepts()) {
                details.add(new ProcessOfTreatmentDetail(new ProcessOfTreatmentDetailID(process.getPotId(), i)));
            }
            potDetailRepository.saveAll(details);
            LOGGER.info("Dang ky cac muc thanh cong");
            List<ResponseDataPOT> responseDataPOTS = new ArrayList<>();
            // tra ve phong tuong ung
            for (Integer i : treatmentDTO.getDepts()) {
                Dept dept = deptRepository.findByDeptId(i);
                List<Doctor> doctors = doctorRepository.findByDeptDoctor(dept);
                ResponseDataPOT responseDataPOT = new ResponseDataPOT();
                for (Doctor d : doctors) {
                    if (d.getActive() == 1) {
                        responseDataPOT.setDoctorName(d.getAccount().getUsername());
                        LOGGER.info("Them phan hoi du lieu bac sy thanh cong : " + d.getAccount().getUsername());
                    }
                }
                responseDataPOT.setDeptRoom(dept.getAddress());
                LOGGER.info("Them phan hoi thong tin phong kham thanh cong : " + dept.getAddress());

                responseDataPOTS.add(responseDataPOT);
            }

            return new OkResponse(responseDataPOTS);

        } else {
            return new BadResponse(MsgRespone.GLOBAL_INPUT_INVALID);
        }

    }
}
