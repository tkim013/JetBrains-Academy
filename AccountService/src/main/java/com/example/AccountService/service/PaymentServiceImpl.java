package com.example.AccountService.service;

import com.example.AccountService.entity.Payment;
import com.example.AccountService.entity.User;
import com.example.AccountService.exception.BadRequestException;
import com.example.AccountService.model.PayrollResponse;
import com.example.AccountService.model.StatusResponse;
import com.example.AccountService.repository.PaymentRepository;
import com.example.AccountService.repository.UserRepository;
import com.example.AccountService.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Month;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public StatusResponse uploadPayroll(List<Payment> payments) {

        //check for unique employee-period pair
        for (int i = 0; i < payments.size(); i++) {

            String[] ep = { payments.get(i).getEmployee(), payments.get(i).getPeriod() };

            for (int j = i; j < payments.size(); j++) {

                if (j == i) {

                    continue;
                }

                String[] ep2 = { payments.get(j).getEmployee(), payments.get(j).getPeriod() };

                if (Arrays.equals(ep, ep2)) {

                    throw new BadRequestException("Duplicate entry found.");
                }
            }
        }

        //check for employee exists in db
        for (Payment p: payments) {

            Optional<User> user = Optional.ofNullable(
                    this.userRepository.findUserByEmail(p.getEmployee().toLowerCase()));

            if (user.isEmpty()) {

                throw new BadRequestException("Employee not in Users table.");
            }

            p.setUser(user.get());
        }

        //check for employee-period already exist in db
        for (Payment p: payments) {

            Optional<Payment> o = this.paymentRepository.findPaymentByEmployeePeriod(
                    p.getEmployee(),
                    p.getPeriod()
            );

            if (o.isPresent()) {

                throw new BadRequestException("Payment employee-period exists in database.");
            }
        }

        for (Payment p : payments) {

            this.paymentRepository.save(p);
        }

        return new StatusResponse("Added successfully!");
    }

    @Override
    public StatusResponse updatePaymentInfo(Payment payment) {

        Optional<Payment> o = this.paymentRepository.findPaymentByEmployeePeriod(
                payment.getEmployee(),
                payment.getPeriod()
        );

        //check for employee-period exist in db
        if (o.isEmpty()) {

            throw new BadRequestException("Payment not found.");
        }

        Payment update = o.get();

        update.setSalary(payment.getSalary());
        this.paymentRepository.save(update);

        return new StatusResponse("Updated successfully!");
    }

    @Override
    public Object getEmployeePayroll(String period, UserDetailsImpl details) {

        if (period != null) {

            //check for valid period format
            if (!Pattern.matches("^(1[0-2]|0[1-9])-\\d\\d\\d\\d$", period)) {

                throw new BadRequestException("Invalid period format.");
            }

            Optional<Payment> o = this.paymentRepository.findPaymentByEmployeePeriod(details.getEmail().toLowerCase(), period);

            //check if payment info exists
            if (o.isEmpty()) {

                throw new BadRequestException("Payroll information not in database.");
            }

            return getPayrollResponse(o.get(), period);

        } else {

            User user = this.userRepository.findUserByEmail(details.getEmail().toLowerCase());

            List<PayrollResponse> prList = new ArrayList<>();

            //sort by period descending
            List<Payment> payments = user.getPayments();

            payments.sort(((Comparator<Payment>) (p1, p2) -> {

                if (Integer.parseInt(p1.getPeriod().substring(3)) == Integer.parseInt(p2.getPeriod().substring(3))) {
                    if (Integer.parseInt(p1.getPeriod().substring(0, 2)) == Integer.parseInt(p2.getPeriod().substring(0, 2))) {
                        return 0;
                    } else {
                        return Integer.parseInt(p1.getPeriod().substring(0, 2)) < Integer.parseInt(p2.getPeriod().substring(0, 2)) ?
                                -1 : 1;
                    }
                } else {
                    return (Integer.parseInt(p1.getPeriod().substring(3)) < Integer.parseInt(p2.getPeriod().substring(3))) ?
                            -1 : 1;
                }
            }).reversed());

            for (Payment p : user.getPayments()) {
                prList.add(getPayrollResponse(p, p.getPeriod()));
            }

            return prList;
        }
    }

    private PayrollResponse getPayrollResponse(Payment payment, String period) {

        PayrollResponse pr = new PayrollResponse();

        pr.setName(payment.getUser().getName());

        pr.setLastname(payment.getUser().getLastname());

        String month = String.valueOf(Month.of(Integer.parseInt(period.substring(0,2)))).toLowerCase();
        pr.setPeriod(month.substring(0, 1).toUpperCase() + month.substring(1) +
                period.substring(2));

        String salary = String.valueOf(payment.getSalary());
        if (salary.length() == 2) {
            pr.setSalary("0 dollar(s) " + salary + " cent(s)");
        } else {
            pr.setSalary(salary.substring(0, salary.length() - 2) + " dollar(s) " +
                    salary.substring(salary.length() - 2) + " cent(s)");
        }
        return pr;
    }
}
