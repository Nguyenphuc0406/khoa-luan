package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.security.CustomUserDetails;
import com.hust.medtech.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements UserDetailsService, AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return new CustomUserDetails(account);
        }
    }


    @Override
    public BaseResponse registerDeviceToken(String token, String accountName) {

        Account accountCheck = accountRepository.findByUsername(accountName);
        if (accountCheck !=null){
            accountRepository.setDeviceTokenByUsername(token, accountCheck.getUsername());
            return new OkResponse("Update device token success !");
        } else {
            return new BadResponse("Update device token fail !");
        }


    }
}
