package account.service;

import account.entity.Group;
import account.entity.SecurityEvent;
import account.entity.User;
import account.exception.BadRequestException;
import account.exception.NotFoundException;
import account.model.*;
import account.repository.GroupRepository;
import account.repository.SecurityEventRepository;
import account.repository.UserRepository;
import account.security.service.UserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

//    public static final int MAX_FAILED_ATTEMPTS = 5;
//
//    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SecurityEventRepository securityEventRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    List<String> breachedPasswords = Arrays.asList("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch",
            "PasswordForApril", "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    @Override
    @Transactional
    public UserResponse registerUser(User user) {

        Optional<User> o = Optional.ofNullable(this.userRepository.findUserByEmail(user.getEmail().toLowerCase()));

        //check if user already exists
        if (o.isPresent()) {

            throw new BadRequestException("User exist!");
        }

        //check password length
        if (user.getPassword().length() < 12) {

            throw new BadRequestException("Password length must be 12 chars minimum!");
        }

        //check for breached password
        if (this.breachedPasswords.contains(user.getPassword())) {

            throw new BadRequestException("The password is in the hacker's database!");
        }

        //bcrypt
        user.setPassword(encoder.encode(user.getPassword()));

        //first user registered granted admin role
        if (userRepository.count() == 0) {

            user.addUserGroups(groupRepository.findByCode("administrator"));

        } else {

            //role user granted after admin registration
            user.addUserGroups(groupRepository.findByCode("user"));
        }

        user.setEmail(user.getEmail().toLowerCase());

        user.setAccountNonLocked(true);
        user.setEnabled(true);

        this.userRepository.save(user);

        //log security event
        this.securityEventRepository.save( new SecurityEvent(LocalDateTime.now(), "CREATE_USER", "Anonymous",
                user.getEmail(), "/api/auth/signup"));

        return new UserResponse(user.getId(), user.getName(), user.getLastname(), user.getEmail(),
                getRoles(user.getUserGroups()));
    }

    @Override
    public ChangePasswordResponse changePassword(NewPassword newpassword, UserDetailsImpl details) {

        User user = this.userRepository.findUserByEmail(details.getEmail().toLowerCase());

        //check for same password
        if (encoder.matches(newpassword.getNew_password(), user.getPassword())) {

            throw new BadRequestException("The passwords must be different!");
        }

        //check password length
        if (newpassword.getNew_password().length() < 12) {

            throw new BadRequestException("Password length must be 12 chars minimum!");
        }

        //check for breached password
        if (this.breachedPasswords.contains(newpassword.getNew_password())) {

            throw new BadRequestException("The password is in the hacker's database!");
        }

        //bcrypt
        user.setPassword(encoder.encode(newpassword.getNew_password()));

        this.userRepository.save(user);

        //log security event
        //log security event
        this.securityEventRepository.save(new SecurityEvent(LocalDateTime.now(), "CHANGE_PASSWORD",
                details.getEmail(), user.getEmail(), "/api/auth/changepass"));

        return new ChangePasswordResponse(user.getEmail().toLowerCase(),
                "The password has been updated successfully");
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<UserResponse> list = new ArrayList<>();
        List<User> users = this.userRepository.findAll();

        //return empty list if no information
        if (users.size() == 0) {
            return list;
        }

        for (User u : users) {
            UserResponse ur = new UserResponse();
            BeanUtils.copyProperties(u, ur);
            ur.setRoles(getRoles(u.getUserGroups()));
            list.add(ur);
        }

        //sort by id ascending
        list.sort(Comparator.comparing(UserResponse::getId));

        return list;
    }

    @Override
    public DeleteUserResponse deleteUserByEmail(String email, UserDetailsImpl details) {

        //check for ADMINISTRATOR removing self
        if (email.equalsIgnoreCase(details.getEmail())) {
            throw new BadRequestException("Can't remove ADMINISTRATOR role!");
        }

        Optional<User> o = Optional.ofNullable(userRepository.findUserByEmail(email.toLowerCase()));

        //check if user exists
        if (o.isEmpty()) {
            throw new NotFoundException("User not found!");
        }

        this.userRepository.deleteUserByEmail(email.toLowerCase());

        this.securityEventRepository.save(new SecurityEvent(LocalDateTime.now(), "DELETE_USER",
                details.getEmail(), email, "/api/admin/user"));

        return new DeleteUserResponse(email, "Deleted successfully!");
    }

    @Override
    @Transactional
    public UserResponse changeUserRole(ChangeRole changeRole, UserDetailsImpl details) {

        Optional<User> userOptional;
        Optional<Group> groupOptional;
        User user;
        Group group;
        UserResponse userResponse;

        userOptional = Optional.ofNullable(this.userRepository.findUserByEmail(changeRole.getUser().toLowerCase()));

        //check for User in database
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found!");
        }

        groupOptional = Optional.ofNullable(this.groupRepository.findByCode(changeRole.getRole().toLowerCase()));

        //check for role in database
        if (groupOptional.isEmpty()) {
            throw new NotFoundException("Role not found!");
        }

        user = userOptional.get();
        group = groupOptional.get();


        switch (changeRole.getOperation()) {

            case "GRANT": {

                for (Group g : user.getUserGroups()) {
                    //check for administrator/business role restriction
                    if (g.getCode().equalsIgnoreCase("administrator")) {
                        if (group.getCode().equalsIgnoreCase("user") ||
                                group.getCode().equalsIgnoreCase("accountant") ||
                                group.getCode().equalsIgnoreCase("auditor")
                        ) {
                            throw new BadRequestException("The user cannot combine administrative and business roles!");
                        }
                    }
                    if (g.getCode().equalsIgnoreCase("user") &&
                            group.getCode().equalsIgnoreCase("administrator")) {
                        throw new BadRequestException("The user cannot combine administrative and business roles!");
                    }
                    if (g.getCode().equalsIgnoreCase("accountant") &&
                            group.getCode().equalsIgnoreCase("administrator")) {
                        throw new BadRequestException("The user cannot combine administrative and business roles!");
                    }
                    if (g.getCode().equalsIgnoreCase("auditor") &&
                            group.getCode().equalsIgnoreCase("administrator")) {
                        throw new BadRequestException("The user cannot combine administrative and business roles!");
                    }
                }

                user.addUserGroups(group);

                //log security event
                this.securityEventRepository.save(new SecurityEvent(LocalDateTime.now(), "GRANT_ROLE",
                        details.getEmail(), "Grant role " + group.getCode().toUpperCase() + " to " + user.getEmail(),
                        "/api/admin/user/role"));

                break;
            }

            case "REMOVE": {

                //check for role not provided to user
                if (!user.getUserGroups().contains(group)) {
                    throw new BadRequestException("The user does not have a role!");
                }
                //check for administrator account
                if (user.getId() == 1 && group.getCode().equalsIgnoreCase("administrator")) {
                    throw new BadRequestException("Can't remove ADMINISTRATOR role!");
                }
                //check for only existing role of user
                if (user.getUserGroups().size() == 1) {
                    throw new BadRequestException("The user must have at least one role!");
                }


                user.removeUserGroups(group);

                //log security event
                this.securityEventRepository.save(new SecurityEvent(LocalDateTime.now(), "REMOVE_ROLE",
                        details.getEmail(), "Remove role " + group.getCode().toUpperCase() + " from " + user.getEmail(),
                        "/api/admin/user/role"));

                break;
            }

            default: {
                throw new BadRequestException("Invalid Operation");
            }
        }

        userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        userResponse.setRoles(getRoles(user.getUserGroups()));

        return userResponse;
    }

    @Override
    public StatusResponse modifyUserAccess(UserOperation userOperation, UserDetailsImpl details) {

        Optional<User> userOptional;
        User user;

        userOptional = Optional.ofNullable(this.userRepository.findUserByEmail(userOperation.getUser().toLowerCase()));

        //check for User in database
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found!");
        }

        user = userOptional.get();

        switch (userOperation.getOperation()) {

            case ("LOCK"): {

                //check for lock on administrator
                if (user.getUserGroups().contains(this.groupRepository.findByCode("administrator"))) {
                    throw new BadRequestException("Can't lock the ADMINISTRATOR!");
                }

                this.lock(user);

                //log security event
                securityEventRepository.save(new SecurityEvent(LocalDateTime.now(), "LOCK_USER",
                        details.getEmail(), "Lock user " + user.getEmail(),
                        "/api/admin/user/access"));

                return new StatusResponse("User " + user.getEmail() + " locked!");
            }

            case ("UNLOCK"): {

                this.unlock(user);

                //log security event
                securityEventRepository.save(new SecurityEvent(LocalDateTime.now(), "UNLOCK_USER",
                        details.getEmail(), "Unlock user " + user.getEmail(),
                        "/api/admin/user/access"));

                return new StatusResponse("User " + user.getEmail() + " unlocked!");
            }

            default: {
                throw new BadRequestException("Invalid operation.");
            }
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    //return List of roles sorted ascending by name
    private List<String> getRoles(Set<Group> groups) {

        List<String> roles = new ArrayList<>();

        for (Group g : groups) {
            roles.add(g.getName());
        }

        Collections.sort(roles);

        return roles;
    }

    @Transactional
    @Modifying
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        this.userRepository.updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    @Transactional
    @Modifying
    public void resetFailedAttempts(String email) {
        this.userRepository.updateFailedAttempts(0, email.toLowerCase());
    }

    public void lock(User user) {

        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        System.out.println(user.getEmail());

        this.userRepository.save(user);
    }

    public void unlock(User user) {
        user.setAccountNonLocked(true);
        user.setLockTime(null);

        this.userRepository.save(user);
    }

//    public boolean unlockWhenTimeExpired(User user) {
//        long lockTimeInMillis = user.getLockTime().getTime();
//        long currentTimeInMillis = System.currentTimeMillis();
//
//        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
//            user.setAccountNonLocked(true);
//            user.setLockTime(null);
//            user.setFailedAttempt(0);
//
//            userRepository.save(user);
//
//            return true;
//        }
//
//        return false;
//    }
}
