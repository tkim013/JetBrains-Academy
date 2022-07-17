package com.example.AccountService.service;

import com.example.AccountService.entity.SecurityEvent;

import java.util.List;

public interface SecurityEventService {

    List<SecurityEvent> findAll();

    void save(SecurityEvent se);
}
