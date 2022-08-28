package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.Ip;
import com.example.AntiFraudSystem.model.StatusResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IpService {

    ResponseEntity<Ip> addSuspiciousIp(Ip ip);

    StatusResponse deleteSuspiciousIp(String ip);

    List<Ip> getAllSuspiciousIp();
}
