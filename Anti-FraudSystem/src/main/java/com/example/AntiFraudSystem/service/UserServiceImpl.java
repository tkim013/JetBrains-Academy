package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.User;
import com.example.AntiFraudSystem.exception.BadRequestException;
import com.example.AntiFraudSystem.exception.ConflictException;
import com.example.AntiFraudSystem.exception.NotFoundException;
import com.example.AntiFraudSystem.model.*;
import com.example.AntiFraudSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public ResponseEntity<UserResponse> addUser(User user) {

        UserResponse userResponse = new UserResponse();

        //bcrypt
        user.setPassword(encoder.encode(user.getPassword()));

        //first user registered granted admin role
        if (userRepository.count() == 0) {

            user.setRole("ADMINISTRATOR");
            user.setEnabled(true);

        } else {

            //role merchant granted after admin registration
            user.setRole("MERCHANT");
            user.setEnabled(false);
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ConflictException("http conflict");
        }

        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole());

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<UserResponse> list = new ArrayList<>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            list.add(new UserResponse(user));
        }
        return list;
    }

    @Override
    public DeleteResponse deleteUserByUsername(String name) {

        Optional<User> o = userRepository.findByUsername(name.toLowerCase());

        if (o.isEmpty()) {
            throw new NotFoundException("not found");
        }

        userRepository.delete(o.get());
        return new DeleteResponse(name);
    }

    @Override
    public UserResponse changeUserRole(ChangeRoleRequest request) {

        Optional<User> o = userRepository.findByUsername(request.getUsername());

        if (o.isEmpty()) {
            throw new NotFoundException("not found");
        }

        if (!request.getRole().equalsIgnoreCase("support") &&
        !request.getRole().equalsIgnoreCase("merchant")) {
            throw new BadRequestException("bad request");
        }

        if (request.getRole().equalsIgnoreCase(o.get().getRole())) {
            throw new ConflictException("conflict");
        }

        o.get().setRole(request.getRole().toUpperCase());

        try {
            userRepository.save(o.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UserResponse(o.get());
    }

    @Override
    public StatusResponse changeUserAccess(AccessRequest request) {

        String operation = "";
        Optional<User> o = userRepository.findByUsername(request.getUsername());

        if (o.isEmpty()) {
            throw new NotFoundException("not found");
        }

        if (o.get().getRole().equalsIgnoreCase("ADMINISTRATOR")) {
            throw new BadRequestException("bad request");
        }

        switch (request.getOperation().toLowerCase()) {

            case "lock": {
                o.get().setEnabled(false);
                operation = "locked";
                break;
            }

            case "unlock": {
                o.get().setEnabled(true);
                operation = "unlocked";
                break;
            }

            default: {
                //invalid operation
                break;
            }
        }

        userRepository.save(o.get());

        return new StatusResponse("User " + o.get().getUsername() + " " + operation + "!");
    }
}
