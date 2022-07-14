package com.example.Recipes.security.service;

import com.example.Recipes.entity.User;
import com.example.Recipes.exception.EmailNotFoundException;
import com.example.Recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        User user = userRepo.findUserByEmail(email);

        if (user == null) {
            throw new EmailNotFoundException("Not found: " + email);
        }

        return new UserDetailsImpl(user);
    }
}