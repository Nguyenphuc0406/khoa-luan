package com.hust.medtech.service.impl;

import com.hust.medtech.base.request.FCMRequest;
import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.ItemOfDeptDTO;
import com.hust.medtech.data.dto.TransactionMedicalDTO;
import com.hust.medtech.data.entity.*;
import com.hust.medtech.data.entity.key.ProcessOfTreatmentDetailID;
import com.hust.medtech.data.entity.key.TransactionMedicalDetailID;
import com.hust.medtech.data.entity.response.DataPayment;
import com.hust.medtech.repository.*;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.TransactionMedicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.Notification;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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
    ProcessOfTreatmentRepository processOfTreatmentRepository;
    @Autowired
    POTDetailRepository potDetailRepository;
    @Autowired
    JwtTokenProvider tokenProvider;

    // danh cho BS dang ky chi muc
    @Transactional(rollbackOn = Exception.class)
    @Override
    public BaseResponse addTransMedical(TransactionMedicalDTO transMedicalDTO, String doctorName) {
        Doctor doctor = doctorRepository.findByAccount_Username(doctorName);
        if (doctor == null) {
            return new NotFoundResponse("Không tìm thấy thông tin bác sĩ");
        }
        Optional<ProcessOfTreatment> ofTreatment = processOfTreatmentRepository.findById(transMedicalDTO.getPotId());
        if (!ofTreatment.isPresent()) {
            return new NotFoundResponse("Không tìm thấy thông tin giao dịch khám của bệnh nhân!");
        }
        Patient patient = ofTreatment.get().getPatientPot();
        TransactionMedical transactionMedical = TransactionMedical.builder()
                .createDate(new Date())
                .doctor(doctor)
                .status(1)
                .patient(patient).build();
        List<TransactionMedicalDetail> details = new ArrayList<>();
        for (Integer i : transMedicalDTO.getItemOfDepts()) {
            Optional<ItemOfDept> item = iodRepository.findById(i);
            if (!item.isPresent()) {
                continue;
            }
            LOGGER.info("Oki");
            details.add(new TransactionMedicalDetail(new
                    TransactionMedicalDetailID(transactionMedical, item.get())));
        }
        transactionMedical.setTransactionMedicalDetails(
                details
        );
        ProcessOfTreatmentDetailID kProcessOfTreatmentDetailID = new ProcessOfTreatmentDetailID();
        kProcessOfTreatmentDetailID.setDeptId(doctor.getDeptDoctor());
        kProcessOfTreatmentDetailID.setPotId(ofTreatment.get());

        Optional<ProcessOfTreatmentDetail> processOfTreatmentDetail = potDetailRepository
                .findById(kProcessOfTreatmentDetailID);
        int currentIndexPot = -1;
        if(processOfTreatmentDetail.isPresent()){
            ProcessOfTreatmentDetail p = processOfTreatmentDetail.get();
            p.setAccStatus(1);
            currentIndexPot = p.getIndexNum();
//            todo notify last 2 account
            potDetailRepository.save(p);
        }
//        them thu n thi notify thang n+1 va n+2;
        Pageable paging = PageRequest.of(0, 2);
        Page<ProcessOfTreatmentDetail>  userNeedNotys =  potDetailRepository._getAccountNotifyByPotId(currentIndexPot,doctor.getDeptDoctor().getDeptId()
        , ofTreatment.get().getPotId(),paging);
        List<String> tokens = new ArrayList<>();
        if(userNeedNotys != null && userNeedNotys.getContent() != null){

            for (ProcessOfTreatmentDetail p : userNeedNotys.getContent()){
                String token;
                try {
                    token =  p.getProcessDetailId().getPotId().getPatientPot().getAccount().getDeviceToken();
                    if(token != null){
                        tokens.add(token);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }
        if(!tokens.isEmpty()){
//            notifies
            RestTemplate restTemplate = new RestTemplate();
            FCMRequest request = new FCMRequest();

            request.registration_ids = tokens;
            FCMRequest.Notification notification = new FCMRequest.Notification();
            notification.title = "Thông báo từ MedTech";
            notification.body = "Sắp đến thứ tự khám của bạn. vui lòng chuẩn bị.";
            request.notification = notification;
            ResponseEntity<String> response
                    = restTemplate.postForEntity("https://fcm.googleapis.com/fcm/send",request, String.class);


        }
        medicalRepository.save(transactionMedical);


        return new OkResponse(MsgRespone.TAO_PHIEU_KHAM_THANH_CONG);
    }

    @Override
    public BaseResponse getTotalPayment(String userName) {
        Pageable paging = PageRequest.of(0, 1);
        Page<TransactionMedical> transactionMedical = medicalRepository.
                _getTransByUserAnDate(userName, paging);

        if (transactionMedical == null || transactionMedical.getContent() == null
                || transactionMedical.getContent().isEmpty()) {
            return new NotFoundResponse("Ban khog co giao dich nao");
        }
        List<ItemOfDeptDTO> itemOfDepts = new ArrayList<>();
        TransactionMedical item = transactionMedical.getContent().get(0);
        AtomicInteger totalPrice = new AtomicInteger();
        item.getTransactionMedicalDetails()
                .forEach(transactionMedicalDetail -> {
                    int p = transactionMedicalDetail.getTransDetailId().
                            getItemOfDeptId().getPrice();
                    ItemOfDeptDTO i = new ItemOfDeptDTO();
                    i.setName(transactionMedicalDetail.getTransDetailId().
                            getItemOfDeptId().getName());
                    i.setPrice(p);
                    itemOfDepts.add(i);
                    totalPrice.addAndGet(p);
                });

        DataPayment dataPayment = new DataPayment();
        dataPayment.setItemOfDepts(itemOfDepts);
        dataPayment.setNamePatient(item.getPatient().getAccount().getFullName());
        dataPayment.setTotalPrice(totalPrice.get());
        dataPayment.setTransMedId(item.getTransId());

        return new OkResponse(dataPayment);
    }


}
