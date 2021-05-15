package com.hust.medtech;

import com.hust.medtech.data.dto.DoctorDTO;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.service.DoctorService;
import com.hust.medtech.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class MedtechApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedtechApplication.class, args);
    }


}
