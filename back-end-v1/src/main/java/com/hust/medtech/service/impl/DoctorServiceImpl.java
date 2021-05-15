package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.DoctorDTO;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.data.entity.Dept;
import com.hust.medtech.data.entity.Doctor;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.repository.DeptRepository;
import com.hust.medtech.repository.DoctorRepository;
import com.hust.medtech.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DeptRepository deptRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public BaseResponse addDoctor(DoctorDTO doctorDTO) {

        if (doctorDTO != null && doctorDTO.getAccount() != null) {
            Account accountCheck = accountRepository.findByUsername(doctorDTO.getAccount().getUsername());
            if (accountCheck == null) {

                Account account = Account.builder()
                        .username(doctorDTO.getAccount().getUsername())
                        .password(passwordEncoder.encode(doctorDTO.getAccount().getPassword()))
                        .fullName(doctorDTO.getAccount().getFullName())
                        .DOB(doctorDTO.getAccount().getDOB())
                        .job(doctorDTO.getAccount().getJob())
                        .nationality(doctorDTO.getAccount().getNationality())
                        .phoneNumber(doctorDTO.getAccount().getPhoneNumber())
                        .identityCard(doctorDTO.getAccount().getIdentityCard())
                        .role(doctorDTO.getAccount().getRole())
                        .code(doctorDTO.getAccount().getCode()).build();

                Dept dept = Dept.builder()
                        .deptId(doctorDTO.getDept().getDeptId()).build();

                Doctor doctor = Doctor.builder()
                        .room(doctorDTO.getRoom())
                        .active(doctorDTO.getActive())
                        .account(account)
                        .deptDoctor(dept).build();
                accountRepository.save(account);
                doctorRepository.save(doctor);
                return new OkResponse(MsgRespone.DOCTOR_ADD_SUCCESS);
            } else {
                return new BadResponse(MsgRespone.GLOBAL_TRUNG_DU_LIEU);
            }

        } else {
            return new BadResponse(MsgRespone.DOCTOR_INPUT_INVALID);

        }

    }

    @Override
    public BaseResponse getAllDoctor() {
        return null;
    }

    @Override
    public BaseResponse getDoctorById(int doctorId) {
        return null;
    }

    @Override
    public BaseResponse deleteDoctor(int doctorId) {
        return null;
    }

    @Override
    public BaseResponse getDoctorByDept(int deptId) {
        if (deptId != 0) {
            Dept dept = deptRepository.findByDeptId(deptId);
            List<Doctor> doctors = doctorRepository.findByDeptDoctor(dept);
            return new OkResponse(doctors);
        } else {
            return new BadResponse(MsgRespone.GLOBAL_INPUT_INVALID);
        }
    }

    @Override
    public BaseResponse getDoctorActive(int checkActive) {
        return null;
    }

    public String addAccount() {

        Account account = Account.builder()
                .username("hoanganh")
                .password("$2y$12$g1CkXMJE5nYkGv8Msq3NOOKZnW8l0tEfSyLVtQsBTjJqWF6lRrzY6")
                .fullName("Nguyen Hoang Anh")
                .DOB(LocalDate.parse("1977-01-01"))
                .job("Bac si")
                .nationality("Viet Nam")
                .phoneNumber("0123456789")
                .identityCard("174568596")
                .role("ROLE_ADMIN")
                .code("BS_002").build();
        accountRepository.save(account);
        return "Saved !";
    }
}
