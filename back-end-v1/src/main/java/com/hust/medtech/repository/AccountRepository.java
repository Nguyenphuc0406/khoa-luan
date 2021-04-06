package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);

    Account findByAccountId(int accountId);

}
