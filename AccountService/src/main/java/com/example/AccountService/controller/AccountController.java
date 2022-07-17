package com.example.AccountService.controller;

import com.example.AccountService.entity.Payment;
import com.example.AccountService.entity.SecurityEvent;
import com.example.AccountService.model.StatusResponse;
import com.example.AccountService.security.service.UserDetailsImpl;
import com.example.AccountService.service.PaymentService;
import com.example.AccountService.service.SecurityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class AccountController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    SecurityEventService securityEventService;

    @GetMapping("/empl/payment")
    @Secured({ "ROLE_USER", "ROLE_ACCOUNTANT"})
    Object getEmployeePayroll(@RequestParam(required = false) String period,
                          @AuthenticationPrincipal UserDetailsImpl details) {

        return this.paymentService.getEmployeePayroll(period, details);
    }

    @PostMapping("/acct/payments")
    @Secured("ROLE_ACCOUNTANT")
    StatusResponse uploadPayroll(@RequestBody(required = false) @NotEmpty(message = "Payment list cannot be empty.")
                                  List<@Valid Payment> payments) {

        return this.paymentService.uploadPayroll(payments);
    }

    @PutMapping("/acct/payments")
    @Secured("ROLE_ACCOUNTANT")
    StatusResponse updatePaymentInfo(@RequestBody @Valid Payment payment) {

        return this.paymentService.updatePaymentInfo(payment);
    }

    @GetMapping("/security/events")
    @Secured("ROLE_AUDITOR")
    List<SecurityEvent> getSecurityEvents() {

        return this.securityEventService.findAll();
    }
}
