package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.Transaction;
import com.example.AntiFraudSystem.model.FeedbackRequest;
import com.example.AntiFraudSystem.model.TransactionRequest;
import com.example.AntiFraudSystem.model.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse evaluateTransaction(TransactionRequest request);

    Transaction addFeedback(FeedbackRequest request);

    List<Transaction> getAllTransaction();

    List<Transaction> getAllTransactionByNumber(String number);
}
