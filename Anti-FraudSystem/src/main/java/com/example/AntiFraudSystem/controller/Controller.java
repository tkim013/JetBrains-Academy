package com.example.AntiFraudSystem.controller;

import com.example.AntiFraudSystem.entity.Card;
import com.example.AntiFraudSystem.entity.Ip;
import com.example.AntiFraudSystem.entity.Transaction;
import com.example.AntiFraudSystem.entity.User;
import com.example.AntiFraudSystem.model.*;
import com.example.AntiFraudSystem.service.CardService;
import com.example.AntiFraudSystem.service.IpService;
import com.example.AntiFraudSystem.service.TransactionService;
import com.example.AntiFraudSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    TransactionService transactionService;
    @Autowired
    UserService userService;
    @Autowired
    IpService ipService;
    @Autowired
    CardService cardService;

    @PostMapping("/antifraud/transaction")
//    @Secured("ROLE_MERCHANT")
    TransactionResponse evaluateTransaction(@RequestBody @Valid TransactionRequest request) {

        return transactionService.evaluateTransaction(request);
    }

    @PostMapping("/auth/user")
    ResponseEntity<UserResponse> registerUser(@RequestBody @Valid User user) {

        return userService.addUser(user);
    }

    @GetMapping("/auth/list")
//    @Secured({ "ROLE_ADMINISTRATOR", "ROLE_SUPPORT"})
    List<UserResponse> getAllUsers() {

        return userService.getAllUsers();
    }

    @DeleteMapping("/auth/user/{username}")
//    @Secured("ROLE_ADMINISTRATOR")
    DeleteResponse deleteUser(@PathVariable String username) {

        return userService.deleteUserByUsername(username);
    }

    @PutMapping("/auth/role")
//    @Secured("ROLE_ADMINISTRATOR")
    UserResponse changeUserRole(@RequestBody ChangeRoleRequest request) {

        return userService.changeUserRole(request);
    }

    @PutMapping("/auth/access")
//    @Secured("ROLE_ADMINISTRATOR")
    StatusResponse changeUserAccess(@RequestBody AccessRequest request) {

        return userService.changeUserAccess(request);
    }

    @PostMapping("/antifraud/suspicious-ip")
    ResponseEntity<Ip> addSuspiciousIp(@RequestBody @Valid Ip ip) {

        return ipService.addSuspiciousIp(ip);
    }

    @DeleteMapping("/antifraud/suspicious-ip/{ip}")
    StatusResponse deleteSuspiciousIp(@PathVariable String ip) {

        return ipService.deleteSuspiciousIp(ip);
    }

    @GetMapping("/antifraud/suspicious-ip")
    List<Ip> getAllSuspiciousIp() {

        return ipService.getAllSuspiciousIp();
    }

    @PostMapping("/antifraud/stolencard")
    ResponseEntity<Card> addStolenCard(@RequestBody @Valid Card card) {

        return cardService.addStolenCard(card);
    }

    @DeleteMapping("/antifraud/stolencard/{number}")
    StatusResponse deleteStolenCard(@PathVariable String number) {

        return cardService.deleteStolenCard(number);
    }

    @GetMapping("/antifraud/stolencard")
    List<Card> getAllStolenCard() {

        return cardService.getAllStolenCard();
    }

    @PutMapping("/antifraud/transaction")
    Transaction addFeedback(@RequestBody FeedbackRequest request) {

        return transactionService.addFeedback(request);
    }

    @GetMapping("/antifraud/history")
    List<Transaction> getAllTransaction() {

        return transactionService.getAllTransaction();
    }

    @GetMapping("/antifraud/history/{number}")
    List<Transaction> getAllTransactionByNumber(@PathVariable String number) {

        return transactionService.getAllTransactionByNumber(number);
    }
}
