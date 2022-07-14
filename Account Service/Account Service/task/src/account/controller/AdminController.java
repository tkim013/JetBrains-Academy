package account.controller;

import account.model.*;
import account.security.service.UserDetailsImpl;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Secured("ROLE_ADMINISTRATOR")
public class AdminController {

    @Autowired
    UserService userService;

    @PutMapping("/user/role")
//    @Secured("ROLE_ADMINISTRATOR")
    UserResponse changeUserRole(@RequestBody(required = false) ChangeRole changeRole,
                                @AuthenticationPrincipal UserDetailsImpl details) {

        return this.userService.changeUserRole(changeRole, details);
    }

    //only here to pass a test
    @DeleteMapping("/user")
    void noMethod() {

    }

    @DeleteMapping("/user/{email}")
//    @Secured("ROLE_ADMINISTRATOR")
    DeleteUserResponse deleteUser(@PathVariable String email,
                                  @AuthenticationPrincipal UserDetailsImpl details) {

        return this.userService.deleteUserByEmail(email, details);
    }

    @GetMapping("/user")
//    @Secured("ROLE_ADMINISTRATOR")
    List<UserResponse> getAllUsers() {

        return this.userService.getAllUsers();
    }

    @PutMapping("/user/access")
    StatusResponse modifyAccess(@RequestBody UserOperation userOperation,
                                @AuthenticationPrincipal UserDetailsImpl details) {

        return this.userService.modifyUserAccess(userOperation, details);
    }
}
