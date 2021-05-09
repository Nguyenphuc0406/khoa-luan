package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.security.CustomUserDetails;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(ConfigUrl.URL_LOGIN)
    public BaseResponse login(@Validated @RequestBody Account request) {

//			// Xác thực từ username và password.

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new OkResponse(jwt);

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context

    }
    @GetMapping("/getUserInfo")
    public BaseResponse getUserInfoByToken(){
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        Account accountParent = accountRepository.findByUsername(patientName);

        accountParent.setPassword(null);
        return new OkResponse(accountParent);
    }

}
