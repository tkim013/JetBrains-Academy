package account.controller;

import account.entity.User;
import account.model.ChangePasswordResponse;
import account.model.NewPassword;
import account.model.UserResponse;
import account.security.service.UserDetailsImpl;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
