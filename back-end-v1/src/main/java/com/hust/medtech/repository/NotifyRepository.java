package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, String> {

    List<Notify> findNotifyByAccountIdOrderByCreateDateDesc(int accountId);
}
