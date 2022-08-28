package com.example.AntiFraudSystem;

import com.example.AntiFraudSystem.entity.Limit;
import com.example.AntiFraudSystem.entity.Role;
import com.example.AntiFraudSystem.repository.LimitRepository;
import com.example.AntiFraudSystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private RoleRepository roleRepository;
    private LimitRepository limitRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository, LimitRepository limitRepository) {
        this.roleRepository = roleRepository;
        this.limitRepository = limitRepository;
        createRoles();
        loadLimit();
    }

    private void createRoles() {
        try {
            if (this.roleRepository.count() == 0) {
                this.roleRepository.save(new Role("administrator", "ROLE_ADMINISTRATOR"));
                this.roleRepository.save(new Role("merchant", "ROLE_MERCHANT"));
                this.roleRepository.save(new Role("support", "ROLE_SUPPORT"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLimit() {
        try {
            if (this.limitRepository.count() == 0) {
                this.limitRepository.save(new Limit(200, 1500));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
