package account.service;

import account.entity.User;
import account.model.*;
import account.security.service.UserDetailsImpl;

import java.util.List;

public interface UserService {

    UserResponse registerUser(User user);

    ChangePasswordResponse changePassword(NewPassword newpassword, UserDetailsImpl details);

    List<UserResponse> getAllUsers();

    DeleteUserResponse deleteUserByEmail(String email, UserDetailsImpl details);

    UserResponse changeUserRole(ChangeRole changeRole, UserDetailsImpl details);

    StatusResponse modifyUserAccess(UserOperation userOperation, UserDetailsImpl details);

    User findUserByEmail(String email);

    void increaseFailedAttempts(User user);

    public void resetFailedAttempts(String email);

    void lock(User user);

//    boolean unlockWhenTimeExpired(User user);
}
