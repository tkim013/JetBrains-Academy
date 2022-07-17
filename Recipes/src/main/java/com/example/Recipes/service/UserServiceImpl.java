package com.example.Recipes.service;

import com.example.Recipes.entity.User;
import com.example.Recipes.exception.UserExistsException;
import com.example.Recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public void addUser(User user) {

        Optional<User> o = Optional.ofNullable(userRepository.findUserByEmail(user.getEmail()));

        if (o.isPresent()) {
            throw new UserExistsException("(Bad Request)");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }
}
