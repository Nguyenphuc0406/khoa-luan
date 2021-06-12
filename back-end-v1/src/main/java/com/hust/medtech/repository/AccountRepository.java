package com.hust.medtech.repository;

import com.hust.medtech.data.dto.AccountDTO;
import com.hust.medtech.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);

    Account findByAccountId(int accountId);

    @Query("select new com.hust.medtech.data.dto.AccountDTO(a) from Account  a where a.username =?1")
    Optional<AccountDTO> _getAccountByAccountId(String username);

    @Transactional
    @Modifying
    @Query("update Account c set c.deviceToken =?1 where c.username = ?2")
    void setDeviceTokenByUsername(String token, String username);

}
