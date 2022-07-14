package com.example.AccountService.repository;

import com.example.AccountService.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE LOWER(p.employee) = ?1 AND p.period = ?2")
    Optional<Payment> findPaymentByEmployeePeriod(String employee, String period);
}
