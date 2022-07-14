package account.service;

import account.entity.SecurityEvent;

import java.util.List;

public interface SecurityEventService {

    List<SecurityEvent> findAll();

    void save(SecurityEvent se);
}
