package com.example.AccountService.controller;

import com.example.AccountService.entity.User;
import com.example.AccountService.model.ChangePasswordResponse;
import com.example.AccountService.model.NewPassword;
import com.example.AccountService.model.UserResponse;
import com.example.AccountService.security.service.UserDetailsImpl;
import com.example.AccountService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    UserResponse registerUser(@RequestBody @Valid User user) {

        return this.userService.registerUser(user);
    }

    @PostMapping("/changepass")
    @Secured({ "ROLE_USER", "ROLE_ACCOUNTANT", "ROLE_ADMINISTRATOR" })
    ChangePasswordResponse changePassword(@RequestBody NewPassword newpassword,
                                          @AuthenticationPrincipal UserDetailsImpl details) {

        return this.userService.changePassword(newpassword, details);
    }
}
