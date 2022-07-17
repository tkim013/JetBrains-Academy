package com.example.AccountService;

import com.example.AccountService.entity.Group;
import com.example.AccountService.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private GroupRepository groupRepository;

    @Autowired
    public DataLoader(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        createRoles();
    }

    private void createRoles() {
        try {
            if (groupRepository.count() == 0) {
                groupRepository.save(new Group("administrator", "ROLE_ADMINISTRATOR"));
                groupRepository.save(new Group("user", "ROLE_USER"));
                groupRepository.save(new Group("accountant", "ROLE_ACCOUNTANT"));
                groupRepository.save(new Group("auditor", "ROLE_AUDITOR"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
