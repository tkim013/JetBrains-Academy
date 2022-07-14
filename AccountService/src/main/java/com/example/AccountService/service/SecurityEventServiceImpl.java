package com.example.AccountService.service;

import com.example.AccountService.entity.SecurityEvent;
import com.example.AccountService.repository.SecurityEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityEventServiceImpl implements SecurityEventService {

    @Autowired
    SecurityEventRepository securityEventRepository;

    @Override
    public List<SecurityEvent> findAll() {
        return this.securityEventRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    @Override
    public void save(SecurityEvent securityEvent) {
        this.securityEventRepository.save(securityEvent);
    }
}
