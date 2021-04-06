package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.AccountDTO;
import com.hust.medtech.data.dto.PatientDTO;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.data.entity.Patient;
import com.hust.medtech.data.request.RequestDataForm;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.repository.PatientRepository;
import com.hust.medtech.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceIpml implements PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public BaseResponse addPatient(PatientDTO patientDTO) {
        if (patientDTO != null && patientDTO.getAccount() != null) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Account accountCheck = accountRepository.findByUsername(patientDTO.getAccount().getUsername());
            if (accountCheck == null) {
                Account account = Account.builder()
                        .username(patientDTO.getAccount().getUsername())
                        .password(passwordEncoder.encode(patientDTO.getAccount().getPassword()))
                        .fullName(patientDTO.getAccount().getFullName())
                        .DOB(patientDTO.getAccount().getDOB())
                        .job(patientDTO.getAccount().getJob())
                        .nationality(patientDTO.getAccount().getNationality())
                        .phoneNumber(patientDTO.getAccount().getPhoneNumber())
                        .identityCard(patientDTO.getAccount().getIdentityCard())
                        .role(patientDTO.getAccount().getRole())
                        .build();
                accountRepository.save(account);
            } else {
                return new BadResponse(MsgRespone.GLOBAL_TRUNG_DU_LIEU);
            }
            if (accountCheck == null) {
                Account account1 = accountRepository.findByUsername(patientDTO.getAccount().getUsername());
                Patient patient = Patient.builder()
                        .age(patientDTO.getAge())
                        .permanentAddress(patientDTO.getPermanentAddress())
                        .accountId(account1.getAccountId()).build();

                patientRepository.save(patient);
                return new OkResponse<String>(MsgRespone.PATIENT_ADD_SUCCESS);

            } else {
                return new BadResponse(MsgRespone.GLOBAL_TRUNG_DU_LIEU);

            }
        } else {
            return new NotFoundResponse(MsgRespone.PATIENT_INPUT_INVALID);
        }
    }


    @Override
    public BaseResponse submitForm(RequestDataForm dataForm) {
        return null;
    }

    @Override
    public BaseResponse getAllPatient() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        if (patients != null) {
            for (Patient p : patients) {
                PatientDTO dto = PatientDTO.builder()
                        .age(p.getAge())
                        .permanentAddress(p.getPermanentAddress()).build();
                patientDTOS.add(dto);
            }
            return new OkResponse(patientDTOS);

        } else {
            return new NotFoundResponse(MsgRespone.PATIENT_NOT_FOUND);
        }
    }

    @Override
    public BaseResponse getPatientById(int patientId) {
        Patient patient = patientRepository.findByPatientId(patientId);
        if (patient != null) {
            Account account = accountRepository.findByAccountId(patient.getAccountId());

            AccountDTO accountDTO = AccountDTO.builder()
                    .accountId(account.getAccountId())
                    .DOB(account.getDOB())
                    .fullName(account.getFullName())
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .job(account.getJob())
                    .nationality(account.getNationality())
                    .phoneNumber(account.getPhoneNumber())
                    .identityCard(account.getIdentityCard())
                    .role(account.getRole()).build();
            PatientDTO patientDTO = PatientDTO.builder()
                    .patientId(patient.getPatientId())
                    .age(patient.getAge())
                    .account(accountDTO).build();
            return new OkResponse(patientDTO);
        } else {
            return new NotFoundResponse(MsgRespone.PATIENT_NOT_FOUND);
        }


    }

    @Override
    public BaseResponse deletePatient(int patientId) {
        return null;
    }
}
