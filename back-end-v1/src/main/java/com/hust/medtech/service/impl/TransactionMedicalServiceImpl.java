package com.hust.medtech.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.hust.medtech.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.Notification;
import javax.transaction.Transactional;
import java.util.*;
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
    @Autowired
    NotifyRepository mNotifyRepository;

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

        List<Notify> notifies = new ArrayList<>();
        Patient patient = ofTreatment.get().getPatientPot();
        TransactionMedical transactionMedical = TransactionMedical.builder()
                .createDate(new Date())
                .doctor(doctor)
                .status(0)
                .processOfTreatment(ofTreatment.get())
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
        LOGGER.info("TAG 1");
        transactionMedical.setTransactionMedicalDetails(

        details);
        LOGGER.info("TAG 2");

        String currentToken = patient.getAccount().getDeviceToken();
        LOGGER.info("TAG 4");

        notifies.add( Notify.builder()
                .accountId(patient.getAccount().getAccountId())
                .type(2)
                .createDate(new Date())
                .title("Thông báo từ MedTech")
                .content("Bạn đã hoàn thành khám lâm sàng, vui lòng xem chi tiết chỉ địch của bác sĩ trong mục lịch khám")
                .build());
        LOGGER.info("TAG 5");
        ProcessOfTreatmentDetailID kProcessOfTreatmentDetailID = new ProcessOfTreatmentDetailID();
        kProcessOfTreatmentDetailID.setDeptId(doctor.getDeptDoctor());
        kProcessOfTreatmentDetailID.setPotId(ofTreatment.get());
        LOGGER.info("TAG 6");
        Optional<ProcessOfTreatmentDetail> processOfTreatmentDetail = potDetailRepository
                .findById(kProcessOfTreatmentDetailID);
        LOGGER.info("TAG 7");
        int currentIndexPot = -1;
        if(processOfTreatmentDetail.isPresent()){
            ProcessOfTreatmentDetail p = processOfTreatmentDetail.get();
            p.setAccStatus(1);
            currentIndexPot = p.getIndexNum();
//            todo notify last 2 account
            potDetailRepository.save(p);
            LOGGER.info("TAG 8");
        }
//        them thu n thi notify thang n+1 va n+2;
        Pageable paging = PageRequest.of(0, 2);
        Page<ProcessOfTreatmentDetail>  userNeedNotys =  potDetailRepository._getAccountNotifyByPotId(currentIndexPot,doctor.getDeptDoctor().getDeptId()
        , ofTreatment.get().getPotId(),paging);
        List<String> tokens = new ArrayList<>();
        LOGGER.info("TAG 9");
        if(userNeedNotys != null && userNeedNotys.getContent() != null){

            for (ProcessOfTreatmentDetail p : userNeedNotys.getContent()){
                notifies.add( Notify.builder()
                        .accountId(p.getProcessDetailId().getPotId().getPatientPot().getAccount().getAccountId())
                        .type(3)
                        .createDate(new Date())
                        .title("Thông báo từ MedTech")
                        .content("Sắp đến lượt khám của bạn. vui long quay lại phòng khám.")
                        .build());
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
            LOGGER.info("TAG 10");
        }
        if(currentToken !=  null){
            List<String> tokenCurrent = new ArrayList<>();
            tokenCurrent.add(currentToken);
            FCMRequest request = new FCMRequest();
            RestTemplate restTemplate = new RestTemplate();

            request.registration_ids = tokenCurrent;
            FCMRequest.Notification notification = new FCMRequest.Notification();
            notification.title = "Thông báo từ MedTech";
            notification.body = "Bạn đã hoàn thành khám lâm sàng. Vui lòng xem chi tiết chỉ định của bác sĩ trong mục lịch khám!";
            request.notification = notification;

            HttpHeaders registrationHeaders = StringUtils.getHeaders();
            registrationHeaders.set("Authorization", "key=AAAAmZOYw60:APA91bG8BYYUTKZ5ZKpU54sNTzR7MVx1Vp_cFgso-hVjfqBrl02MJ4yVDQZdMuJjHv6gXr3MDJAhC0jxF7xunCyegOL2WR0-lHrh_j9mGcVBn-z1ssk5ka25g90f8oZEgkKAGE2f3ZrA");
            try {
                HttpEntity<String> registrationEntity = new HttpEntity<String>(StringUtils.getBody(request),
                        registrationHeaders);
                ResponseEntity<String> response = restTemplate.exchange("https://fcm.googleapis.com/fcm/send",
                        HttpMethod.POST, registrationEntity,
                        String.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            LOGGER.info("TAG 11");
        }
        if(!tokens.isEmpty()){
//            notifies
            LOGGER.info("TAG 12");
            FCMRequest request = new FCMRequest();
        RestTemplate restTemplate = new RestTemplate();

        request.registration_ids = tokens;
        FCMRequest.Notification notification = new FCMRequest.Notification();
        notification.title = "Thông báo từ MedTech";
        notification.body = "Sắp đến lượt khám của bạn. Vui lòng quay lại phòng khám!";
        request.notification = notification;

        HttpHeaders registrationHeaders = StringUtils.getHeaders();
        registrationHeaders.set("Authorization", "key=AAAAmZOYw60:APA91bG8BYYUTKZ5ZKpU54sNTzR7MVx1Vp_cFgso-hVjfqBrl02MJ4yVDQZdMuJjHv6gXr3MDJAhC0jxF7xunCyegOL2WR0-lHrh_j9mGcVBn-z1ssk5ka25g90f8oZEgkKAGE2f3ZrA");
        try {
            HttpEntity<String> registrationEntity = new HttpEntity<String>(StringUtils.getBody(request),
                    registrationHeaders);
            ResponseEntity<String> response = restTemplate.exchange("https://fcm.googleapis.com/fcm/send",
                    HttpMethod.POST, registrationEntity,
                    String.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        }

        if(!notifies.isEmpty()){
            LOGGER.info("TAG 13");
            mNotifyRepository.saveAll(notifies);
        }
        LOGGER.info("TAG 13");
        medicalRepository.save(transactionMedical);
        medicalDetailRepository.saveAll(transactionMedical.getTransactionMedicalDetails());
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
