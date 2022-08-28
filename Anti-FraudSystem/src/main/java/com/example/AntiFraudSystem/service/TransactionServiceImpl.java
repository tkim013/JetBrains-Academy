package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.Limit;
import com.example.AntiFraudSystem.entity.Transaction;
import com.example.AntiFraudSystem.exception.BadRequestException;
import com.example.AntiFraudSystem.exception.ConflictException;
import com.example.AntiFraudSystem.exception.NotFoundException;
import com.example.AntiFraudSystem.exception.UnprocessableEntityException;
import com.example.AntiFraudSystem.model.FeedbackRequest;
import com.example.AntiFraudSystem.model.TransactionRequest;
import com.example.AntiFraudSystem.model.TransactionResponse;
import com.example.AntiFraudSystem.repository.CardRepository;
import com.example.AntiFraudSystem.repository.IpRepository;
import com.example.AntiFraudSystem.repository.LimitRepository;
import com.example.AntiFraudSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    private int allowed = -1;
    private int manual = -1;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    IpRepository ipRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    LimitRepository limitRepository;

    @Override
    public TransactionResponse evaluateTransaction(TransactionRequest request) {

        int regionCount = 0;
        int ipCount = 0;

        List<Transaction> transactionList;
        TransactionResponse response;
        Transaction transaction;
        Set<String> regionSet = new HashSet<>();
        Set<String> ipSet = new HashSet<>();

        //loading limit values from db to pass final test
        if (allowed == -1 && manual == -1) {
            Limit limit = limitRepository.findById(1L).get();
            allowed = limit.getAllowed();
            manual = limit.getManual();
        }

        if (request.getAmount() <= 0) {
            //throw bad request error
            throw new BadRequestException("amount must be greater than 0");
        }

        if (!CardServiceImpl.validateCard(request.getNumber())) {
            throw new BadRequestException("card validation failed");
        }

        response = new TransactionResponse();

        if (request.getAmount() <= allowed) {
            //allowed
            response.setResult("ALLOWED");
            response.setInfo("none");
        } else if (request.getAmount() <= manual) {
            //manual_processing
            response.setResult("MANUAL_PROCESSING");
            response.setInfo("amount");
        } else {
            //prohibited
            response.setResult("PROHIBITED");
            response.setInfo("amount");
        }

        if (cardRepository.findByNumber(request.getNumber()).isPresent()) {

            if (response.getResult().equals("PROHIBITED")) {
                response.setInfo(response.getInfo() + ", card-number");
            } else {
                response.setResult("PROHIBITED");
                response.setInfo("card-number");
            }
        }

        if (ipRepository.findByIp(request.getIp()).isPresent()) {

            if (response.getResult().equals("PROHIBITED")) {
                response.setInfo(response.getInfo() + ", ip");
            } else {
                response.setResult("PROHIBITED");
                response.setInfo("ip");
            }
        }

        transactionList = transactionRepository.findAllTransactionByNumberAndDateBetween(
                request.getNumber(),
                LocalDateTime.parse(request.getDate()).minus(1, ChronoUnit.HOURS),
                LocalDateTime.parse(request.getDate())
        );

        for (Transaction t : transactionList) {
            regionSet.add(t.getRegion());
            ipSet.add(t.getIp());
        }

        for (String s: regionSet) {
            if (!s.equals(request.getRegion())) {
                regionCount++;
            }
        }

        for (String s: ipSet) {
            if (!s.equals(request.getIp())) {
                ipCount++;
            }
        }

        if (ipCount == 2) {
            if (response.getResult().equals("ALLOWED")) {
                response.setResult("MANUAL_PROCESSING");
                response.setInfo("ip-correlation");
            } else if (response.getResult().equals("MANUAL_PROCESSING")) {
                response.setInfo(response.getInfo() + ", ip-correlation");
            }
        } else if (ipCount > 2) {
            if (response.getResult().equals("PROHIBITED")) {
                response.setInfo(response.getInfo() + ", ip-correlation");
            } else {
                response.setResult("PROHIBITED");
                response.setInfo("ip-correlation");
            }
        }

        if (regionCount == 2) {
            if (response.getResult().equals("ALLOWED")) {
                response.setResult("MANUAL_PROCESSING");
                response.setInfo("region-correlation");
            } else if (response.getResult().equals("MANUAL_PROCESSING")) {
                response.setInfo(response.getInfo() + ", region-correlation");
            }
        } else if (regionCount > 2) {
            if (response.getResult().equals("PROHIBITED")) {
                response.setInfo(response.getInfo() + ", region-correlation");
            } else {
                response.setResult("PROHIBITED");
                response.setInfo("region-correlation");
            }
        }

        transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setIp(request.getIp());
        transaction.setNumber(request.getNumber());
        transaction.setRegion(request.getRegion());
        transaction.setDate(LocalDateTime.parse(request.getDate()));
        transaction.setResult(response.getResult());
        transaction.setFeedback("");

        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Transaction addFeedback(FeedbackRequest request) {

        Optional<Transaction> o = transactionRepository.findById(request.getTransactionId());
        Transaction transaction;

        Limit limit = limitRepository.findById(1L).get();
        //loading limit values from db to pass final test
        if (allowed == -1 && manual == -1) {
            allowed = limit.getAllowed();
            manual = limit.getManual();
        }

        //exists check
        if (o.isEmpty()) {
            throw new NotFoundException("transaction not found in history");
        }

        transaction = o.get();

        //String format check
        if (!request.getFeedback().equals("ALLOWED") &&
                !request.getFeedback().equals("MANUAL_PROCESSING") &&
                !request.getFeedback().equals("PROHIBITED")
        ) {
            throw new BadRequestException("wrong format");
        }

        //empty String check in transaction.feedback
        if (!transaction.getFeedback().equals("")) {
            throw new ConflictException("feedback already exists in database");
        }

        //check feedback for exception
        if (request.getFeedback().equals(transaction.getResult())) {
            throw new UnprocessableEntityException("result same as feedback");
        }

        switch (request.getFeedback()) {

            case "ALLOWED": {

                switch (transaction.getResult()) {

                    case "MANUAL_PROCESSING": {
                        allowed = increaseLimit(allowed, Math.toIntExact(transaction.getAmount()));
                        break;
                    }

                    case "PROHIBITED": {
                        allowed = increaseLimit(allowed, Math.toIntExact(transaction.getAmount()));
                        manual = increaseLimit(manual, Math.toIntExact(transaction.getAmount()));
                        break;
                    }

                    default: {
                        //should never get here
                        break;
                    }
                }
                break;
            }

            case "MANUAL_PROCESSING": {

                switch (transaction.getResult()) {

                    case "ALLOWED": {
                        allowed = decreaseLimit(allowed, Math.toIntExact(transaction.getAmount()));
                        break;
                    }

                    case "PROHIBITED": {
                        manual = increaseLimit(manual, Math.toIntExact(transaction.getAmount()));
                        break;
                    }

                    default: {
                        //should never get here
                        break;
                    }
                }
                break;
            }

            case "PROHIBITED": {

                switch (transaction.getResult()) {

                    case "ALLOWED": {
                        allowed = decreaseLimit(allowed, Math.toIntExact(transaction.getAmount()));
                        manual = decreaseLimit(manual, Math.toIntExact(transaction.getAmount()));
                        break;
                    }

                    case "MANUAL_PROCESSING": {
                        manual = decreaseLimit(manual, Math.toIntExact(transaction.getAmount()));
                        break;
                    }

                    default: {
                        //should never get here
                        break;
                    }
                }
                break;
            }

            default: {
                throw new BadRequestException("wrong format");
            }
        }

        transaction.setFeedback(request.getFeedback());
        limit.setAllowed(allowed);
        limit.setManual(manual);

        transactionRepository.save(transaction);
        limitRepository.save(limit);

        return transaction;
    }

    @Override
    public List<Transaction> getAllTransaction() {

        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getAllTransactionByNumber(String number) {

        List<Transaction> list;

        if (!CardServiceImpl.validateCard(number)) {

            throw new BadRequestException("card number not valid");
        }

        list = transactionRepository.findAllTransactionByNumber(number);

        if (list.isEmpty()) {

            throw new NotFoundException("transactions for card number not found");
        }

        return list;
    }

    private int increaseLimit(int currentLimit, int transactionValue) {

        return (int) Math.ceil(0.8 * currentLimit + 0.2 * transactionValue);
    }

    private int decreaseLimit(int currentLimit, int transactionValue) {

        return (int) Math.ceil(0.8 * currentLimit - 0.2 * transactionValue);
    }
}
