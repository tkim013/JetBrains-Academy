package com.example.AccountService.service;

import com.example.AccountService.entity.Payment;
import com.example.AccountService.model.StatusResponse;
import com.example.AccountService.security.service.UserDetailsImpl;

import java.util.List;

public interface PaymentService {

    StatusResponse uploadPayroll(List<Payment> payments);

    StatusResponse updatePaymentInfo(Payment payment);

    Object getEmployeePayroll(String period, UserDetailsImpl details);
}
