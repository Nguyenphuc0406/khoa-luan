package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.ProcessOfTreatmentDTO;
import com.hust.medtech.data.entity.*;
import com.hust.medtech.data.entity.key.ProcessOfTreatmentDetailID;
import com.hust.medtech.data.entity.response.ResponseDataPOT;
import com.hust.medtech.repository.*;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.POTService;
import com.hust.medtech.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    IODRepository iodRepository;
    @Autowired
    DeptRepository deptRepository;

    @Override
    public BaseResponse addPOT(ProcessOfTreatmentDTO treatmentDTO, String token) {
        if (treatmentDTO != null) {
//            String patientName = tokenProvider.getUserNameFromJWT(token);
            Account accountParent = accountRepository.findByUsername(token);
            Patient patient = patientRepository.findByAccount_AccountId(accountParent.getAccountId());
            ProcessOfTreatment process = ProcessOfTreatment.builder()
                    .description(treatmentDTO.getDescription())
                    .dateOfExamination(new Date())
                    .code(StringUtils.randomCode())
                    .patientPot(patient).build();
            potRepository.save(process);
            // ghi cac giao dich vao ban ghi
            List<ProcessOfTreatmentDetail> details = new ArrayList<>();
            for (Integer i : treatmentDTO.getDepts()) {
                Optional<Dept> dp = deptRepository.findById(i);
                if (!dp.isPresent()) {
                    continue;
                }
                List<Object[]> count = potDetailRepository._getCountNumberDept(dp.get().getDeptId());
                if (count == null || count.isEmpty() || count.get(0) == null) {
                    continue;
                }
                int indexNum = Integer.parseInt(count.get(0)[0].toString()) + 1;

                details.add(new ProcessOfTreatmentDetail(new ProcessOfTreatmentDetailID(
                        process, dp.get()), indexNum,0));
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

    @Override
    public BaseResponse getDataMedical(String patientName) {
        Pageable paging = PageRequest.of(0, 1);
        Page<ProcessOfTreatment> pageData = potRepository
                ._getProcessByUsernameAndDay(patientName, paging);
        if (pageData == null || pageData.getContent() == null || pageData.getContent().isEmpty()) {
            return new NotFoundResponse("Không tìm thấy lịch khám của bạn trong ngày hôm nay?");
        }

        ProcessOfTreatment processOfTreatment = pageData.getContent().get(0);
        if (processOfTreatment.getProcessOfTreatmentDetails() == null ||
                processOfTreatment.getProcessOfTreatmentDetails().isEmpty()) {
            return new NotFoundResponse("Không tìm thấy lịch khám của bạn trong ngày hôm nay?");
        }
        List<ResponseDataPOT> list = new ArrayList<>();
        for (ProcessOfTreatmentDetail process : processOfTreatment.getProcessOfTreatmentDetails()) {
            Doctor doctor = null;
            for (Doctor d : process.getProcessDetailId().getDeptId().getDoctors()) {
                if (d.getActive() == 1) {
                    doctor = d;
                    continue;
                }
            }
            if (doctor == null) {
                return new NotFoundResponse("Chưa có bác sĩ nào đảm nhận phòng " + process.getProcessDetailId().getDeptId().getAddress());
            }
            List<Object[]> count = potDetailRepository._getCountNumberDept(process.
                    getProcessDetailId().getDeptId().getDeptId());
            if (count == null || count.isEmpty() || count.get(0) == null) {
                continue;
            }
            int indexNum = Integer.parseInt(count.get(0)[0].toString());
            list.add(ResponseDataPOT.builder()
                    .deptRoom(process.getProcessDetailId().getDeptId().getAddress())
                    .doctorName(doctor.getAccount().getFullName())
                    .doctorPhone(doctor.getAccount().getPhoneNumber())
                    .index(process.getIndexNum())
                    .totalIndex(indexNum)
                    .build());
        }
        return new OkResponse(list);
    }
}
