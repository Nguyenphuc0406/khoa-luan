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
import java.util.*;

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
    public BaseResponse addPOT(ProcessOfTreatmentDTO treatmentDTO, String patientName) {
        if (treatmentDTO != null) {
//            String patientName = tokenProvider.getUserNameFromJWT(token);
            Account accountParent = accountRepository.findByUsername(patientName);
            Patient patient = patientRepository.findByAccount_AccountId(accountParent.getAccountId());

            Pageable paging = PageRequest.of(0, 1);
            Page<ProcessOfTreatment> pageData = potRepository
                    ._getProcessByUsernameAndDay(patientName, paging);
            ProcessOfTreatment process;
            if (pageData != null && pageData.getContent() != null && !pageData.getContent().isEmpty()) {
                process = pageData.getContent().get(0);
                process.getProcessOfTreatmentDetails();
                Map<String, String> mapDept = new HashMap<>();
                for (ProcessOfTreatmentDetail processOfTreatmentDetail : process.getProcessOfTreatmentDetails()) {
                    String key = processOfTreatmentDetail.getProcessDetailId().getDeptId().getDeptId() + "";
                    mapDept.put(key, processOfTreatmentDetail.getProcessDetailId().getDeptId().getName());
                }
                for (Integer i : treatmentDTO.getDepts()) {
                    if (mapDept.containsKey(i + "")) {
                        return new BadResponse("Bạn đã đăng kí " + mapDept.get(i + "") + " trong hôm nay. và không được đăng kí lại.");
                    }
                }

            } else {
                process = ProcessOfTreatment.builder()
                        .description(treatmentDTO.getDescription())
                        .dateOfExamination(new Date())
                        .code(StringUtils.randomCode())
                        .patientPot(patient).build();
                potRepository.save(process);
            }


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
                        process, dp.get()), indexNum, 0));
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
                    .deptName(process.getProcessDetailId().getDeptId().getName())
                    .doctorName(doctor.getAccount().getFullName())
                    .createDate(processOfTreatment.getDateOfExamination().getTime())
                    .isDoctorAccepted(process.getAccStatus())
                    .deptId(process.getProcessDetailId().getDeptId().getDeptId())
                    .doctorPhone(doctor.getAccount().getPhoneNumber())
                    .index(process.getIndexNum())
                    .potId(process.getProcessDetailId().getPotId().getPotId())
                    .totalIndex(indexNum)
                    .build());
        }
        return new OkResponse(list);
    }

    @Override
    public BaseResponse getPatientMedicalByDay(String doctorName) {
        Account accountParent = accountRepository.findByUsername(doctorName);
        if (accountParent == null || accountParent.getDoctor() == null) {
            return new NotFoundResponse("Không tìm thấy thông tin bác sĩ!");
        }
        Doctor doctor = accountParent.getDoctor();
        if (doctor.getDeptDoctor() == null || doctor.getDeptDoctor().getDeptId() < 1) {
            return new NotFoundResponse("Bác sĩ không quản lý phòng khám nào.");
        }
        int depID = doctor.getDeptDoctor().getDeptId();


        return new OkResponse(potDetailRepository._getPostByDoctor(depID));
    }
}
