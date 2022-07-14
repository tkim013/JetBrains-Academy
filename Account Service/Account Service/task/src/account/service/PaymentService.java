package account.service;

import account.entity.Payment;
import account.model.StatusResponse;
import account.security.service.UserDetailsImpl;

import java.util.List;

public interface PaymentService {

    StatusResponse uploadPayroll(List<Payment> payments);

    StatusResponse updatePaymentInfo(Payment payment);

    Object getEmployeePayroll(String period, UserDetailsImpl details);
}
