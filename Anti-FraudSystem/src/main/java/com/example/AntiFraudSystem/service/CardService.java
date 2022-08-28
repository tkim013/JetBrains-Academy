package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.Card;
import com.example.AntiFraudSystem.model.StatusResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CardService {

    ResponseEntity<Card> addStolenCard(Card card);

    StatusResponse deleteStolenCard(String number);

    List<Card> getAllStolenCard();
}
