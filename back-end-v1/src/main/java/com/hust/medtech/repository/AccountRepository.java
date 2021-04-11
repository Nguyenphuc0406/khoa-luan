package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface AccountRepository  extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);

    Account findByAccountId(int accountId);



}
