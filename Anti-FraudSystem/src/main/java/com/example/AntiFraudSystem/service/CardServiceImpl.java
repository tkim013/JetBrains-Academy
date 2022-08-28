package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.Card;
import com.example.AntiFraudSystem.exception.BadRequestException;
import com.example.AntiFraudSystem.exception.ConflictException;
import com.example.AntiFraudSystem.exception.NotFoundException;
import com.example.AntiFraudSystem.model.StatusResponse;
import com.example.AntiFraudSystem.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public ResponseEntity<Card> addStolenCard(Card card) {

        if (!validateCard(card.getNumber())) {
            throw new BadRequestException("card failed validation");
        }

        //exists check
        Optional<Card> o = cardRepository.findByNumber(card.getNumber());

        if (o.isPresent()) {
            throw new ConflictException("card already in database");
        }

        cardRepository.save(card);

        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @Override
    public StatusResponse deleteStolenCard(String number) {

        if (!validateCard(number)) {
            throw new BadRequestException("card failed validation");
        }

        //exists check
        Optional<Card> o = cardRepository.findByNumber(number);

        if (o.isEmpty()) {
            throw new NotFoundException("card does not exist in database");
        }

        cardRepository.delete(o.get());

        return new StatusResponse("Card " + number + " successfully removed!");
    }

    @Override
    public List<Card> getAllStolenCard() {

        return cardRepository.findAll();
    }

    public static boolean validateCard(String number) {

        int[] array = new int[15];

        //last number in string - checksum
        int sum = Integer.parseInt(String.valueOf(number.charAt(number.length() - 1)));

        //load numbers into array
        for (int i = number.length() - 2; i >= 0; i--) {
            array[i] = Integer.parseInt(String.valueOf(number.charAt(i)));
        }

        //traverse array, double odd digits, subtract by 9 if over 9
        for (int i = array.length - 1; i >= 0; i -= 2) {
            array[i] *= 2;
            if (array[i] > 9) {
                array[i] -= 9;
            }
        }

        //add all numbers
        for (int i : array) {
            sum += i;
        }

        return sum % 10 == 0;
    }
}
