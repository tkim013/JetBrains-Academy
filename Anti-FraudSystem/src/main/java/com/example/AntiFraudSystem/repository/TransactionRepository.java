package com.example.AntiFraudSystem.repository;

import com.example.AntiFraudSystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllTransactionByNumberAndDateBetween(String number, LocalDateTime dt1, LocalDateTime dt2);

    List<Transaction> findAllTransactionByNumber(String number);
}
