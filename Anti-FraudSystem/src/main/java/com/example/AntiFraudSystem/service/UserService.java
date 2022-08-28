package com.example.AntiFraudSystem.service;

import com.example.AntiFraudSystem.entity.User;
import com.example.AntiFraudSystem.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<UserResponse> addUser(User user);

    List<UserResponse> getAllUsers();

    DeleteResponse deleteUserByUsername(String name);

    UserResponse changeUserRole(ChangeRoleRequest request);

    StatusResponse changeUserAccess(AccessRequest request);
}